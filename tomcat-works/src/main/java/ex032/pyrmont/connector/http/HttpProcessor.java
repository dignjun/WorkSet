package ex032.pyrmont.connector.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.util.StringManager;

import ex03.pyrmont.connector.util.HttpHeader;
import ex03.pyrmont.connector.util.HttpRequestLine;
import ex03.pyrmont.connector.util.SocketInputStream;
import ex032.pyrmont.connector.HttpConnector;

public class HttpProcessor {

	@SuppressWarnings("unused")
	private HttpConnector connector = null;
	private HttpRequest request;
	private HttpRequestLine requestLine = new HttpRequestLine();
	private HttpResponse response;

	protected String method = null;
	protected String queryString = null;
	protected StringManager sm = StringManager
			.getManager("ex032.pyrmont.connector.http");

	public HttpProcessor(HttpConnector connector) {
		this.connector = connector;
	}

	public void process(Socket socket) {
		SocketInputStream input = null;
		OutputStream output = null;
		try {
			input = new SocketInputStream(socket.getInputStream(), 2048);
			output = socket.getOutputStream();

			// create httprequest object and parse
			request = new HttpRequest(input);
			// create httpresponse object
			response = new HttpResponse(output);
			response.setRequest(request);
			response.setHeader("Server", "Pyrmont Servlet Container");

			parseHeaders(input);
			parseRequest(input, output);

			if(request.getRequestURI().startsWith("/servlet/")){
				ServletProcessor processor = new ServletProcessor();
				processor.process(request, response);
			} else {
				StaticResourceProcessor processor = new StaticResourceProcessor();
				processor.process(request, response);
			}
			
			socket.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 这个方法严重问题，HttpHeader是默认的访问权限修饰符，而且是final关键字修饰的，所以这里将形参也重写一下。
	private void parseHeaders(SocketInputStream input) throws IOException,
			ServletException {
		while (true) {
			HttpHeader header = new HttpHeader();
			input.readHeader(header);
			if (header.nameEnd == 0) {
				if (header.valueEnd == 0) {
					return;
				} else {
					throw new ServletException(
							sm.getString("httpProcessor.parseHeaders.colon"));
				}
			}
			String name = new String(header.name, 0, header.nameEnd);
			String value = new String(header.value, 0, header.valueEnd);
			request.addHeader(name, value);
			if (name.equals("cookie")) {
				Cookie cookies[] = RequestUtil.parseCookieHeader(value);
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("jsessionid")) {
						if (!request.isRequestedSessionIdFromCookie()) {
							request.setRequestedSessionId(cookies[i].getValue());
							request.setRequestedSessionCookie(true);
							request.setRequestedSessionURL(false);
						}
					}
					request.addCookies(cookies[i]);
				}
			} else if (name.equals("content-length")) {
				int n = -1;
				try {
					n = Integer.parseInt(value);
				} catch (Exception e) {
					throw new ServletException(
							sm.getString("httpProcessor.parseHeaders.contentLength"));
				}
				request.setContentLength(n);
			} else if (name.equals("content-type")) {
				request.setContentType(value);
			}
		}
	}

	private void parseRequest(SocketInputStream input, OutputStream output)
			throws IOException, ServletException {
		input.readRequestLine(requestLine);
		String method = new String(requestLine.method, 0, requestLine.methodEnd);
		String uri = null;
		String protocol = new String(requestLine.protocol, 0,
				requestLine.protocolEnd);
		if (method.length() < 1) {
			throw new ServletException("Missing HTTP request method");
		} else if (requestLine.uriEnd < 1) {
			throw new ServletException("Missing HTTP request URI");
		}
		int question = requestLine.indexOf("?");
		if (question >= 0) {
			request.setQueryString(new String(requestLine.uri, question + 1,
					requestLine.uriEnd - question - 1));
			uri = new String(requestLine.uri, 0, question);
		} else {
			request.setQueryString(null);
			uri = new String(requestLine.uri, 0, requestLine.uriEnd);
		}

		// checking for an absolute URI(with the HTTP protocol)
		if (!uri.startsWith("/")) {
			int pos = uri.indexOf("://");
			if (pos != -1) {
				pos = uri.indexOf('/', pos + 3);
				if (pos == -1) {
					uri = "";
				} else {
					uri = uri.substring(pos);
				}
			}
		}

		// parse any requested session id out of the request uri
		String match = ";jsessionid=";
		int semicolon = uri.indexOf(match);
		if (semicolon >= 0) {
			String rest = uri.substring(semicolon + match.length());
			int semicolon2 = rest.indexOf(';');
			if (semicolon2 >= 0) {
				request.setRequestedSessionId(rest.substring(0, semicolon2));
				rest = rest.substring(semicolon2);
			} else {
				request.setRequestedSessionId(rest);
				rest = "";
			}
			request.setRequestedSessionURL(true);
			uri = uri.substring(0, semicolon) + rest;
		} else {
			request.setRequestedSessionId(null);
			request.setRequestedSessionURL(false);
		}

		String normalizedUri = normalize(uri);
		request.setMethod(method);
		request.setProtocol(protocol);
		if (normalizedUri != null) {
			request.setRequestURI(normalizedUri);
		} else {
			request.setRequestURI(uri);
		}

		if (normalizedUri == null) {
			throw new ServletException("Invalid URI:" + uri + "'");
		}
	}

	// uri的标准化
	protected String normalize(String path) {
		if (path == null)
			return null;
		// Create a place for the normalized path
		String normalized = path;

		// Normalize "/%7E" and "/%7e" at the beginning to "/~"
		if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
			normalized = "/~" + normalized.substring(4);

		// Prevent encoding '%', '/', '.' and '\', which are special reserved
		// characters
		if ((normalized.indexOf("%25") >= 0)
				|| (normalized.indexOf("%2F") >= 0)
				|| (normalized.indexOf("%2E") >= 0)
				|| (normalized.indexOf("%5C") >= 0)
				|| (normalized.indexOf("%2f") >= 0)
				|| (normalized.indexOf("%2e") >= 0)
				|| (normalized.indexOf("%5c") >= 0)) {
			return null;
		}

		if (normalized.equals("/."))
			return "/";

		// Normalize the slashes and add leading slash if necessary
		if (normalized.indexOf('\\') >= 0)
			normalized = normalized.replace('\\', '/');
		if (!normalized.startsWith("/"))
			normalized = "/" + normalized;

		// Resolve occurrences of "//" in the normalized path
		while (true) {
			int index = normalized.indexOf("//");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index)
					+ normalized.substring(index + 1);
		}

		// Resolve occurrences of "/./" in the normalized path
		while (true) {
			int index = normalized.indexOf("/./");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index)
					+ normalized.substring(index + 2);
		}

		// Resolve occurrences of "/../" in the normalized path
		while (true) {
			int index = normalized.indexOf("/../");
			if (index < 0)
				break;
			if (index == 0)
				return (null); // Trying to go outside our context
			int index2 = normalized.lastIndexOf('/', index - 1);
			normalized = normalized.substring(0, index2)
					+ normalized.substring(index + 3);
		}

		// Declare occurrences of "/..." (three or more dots) to be invalid
		// (on some Windows platforms this walks the directory tree!!!)
		if (normalized.indexOf("/...") >= 0)
			return (null);

		// Return the normalized path that we have completed
		return (normalized);

	}

}
