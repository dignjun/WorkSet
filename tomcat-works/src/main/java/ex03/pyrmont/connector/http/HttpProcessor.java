package ex03.pyrmont.connector.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.ServletException;

import ex03.pyrmont.connector.util.HttpRequestLine;
import ex03.pyrmont.connector.util.SocketInputStream;
import ex03.pyrmont.connector.util.StringManager;

/**
 * SocketInputStream这个类的导入相当不易，特地下载了tomcat4，并引用了它的lib才可以使用这个类
 * 在更高版本的tomcat中已经将相关的连接器替换了，这个类也就不可以使用了，其实jdk中也是有的，不过是并不是public的。而是默认的修饰符
 * 
 * @author DINGJUN
 *
 */
public class HttpProcessor {

	private HttpRequest request;
	private HttpResponse response;
	@SuppressWarnings("unused")
	private HttpConnector httpConnector;
	private HttpRequestLine requestLine = new HttpRequestLine();

	// 使用org.apache.catalina.util.StringManager类发送错误信息。
	protected StringManager sm = StringManager
			.getManager("ex03.pyrmont.connector.http");

	public HttpProcessor(HttpConnector httpConnector) {
		this.httpConnector = httpConnector;
	}

	public void process(Socket socket) {
		SocketInputStream input = null;
		OutputStream output = null;
		try {
			input = new SocketInputStream(socket.getInputStream(), 2048);
			output = socket.getOutputStream();
			// 创建httprequest对象然后解析请求
			request = new HttpRequest(input);
			response = new HttpResponse(output);
			response.setHttpRequest(request);
			response.setHeader("Server", "Pyrmont Servlet Container");
			parseRequest(input, output);
			parseHeaders(input);
			if (request.getUri().startsWith("/servlet/")) {
				// servlet的处理
				ServletProcessor processor = new ServletProcessor();
				processor.process(request, response);
			} else {
				// 静态资源的处理
				StaticResourceProcessor processor = new StaticResourceProcessor();
				processor.process(request, response);
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseHeaders(SocketInputStream input) {

	}

	private void parseRequest(SocketInputStream input, OutputStream output)
			throws IOException, ServletException {
		// 解析请求行
		input.readRequestLine(requestLine);
		// 获取请求行的方法
		String method = new String(requestLine.method, 0, requestLine.methodEnd);
		String uri = null;
		String protocol = new String(requestLine.protocol, 0,
				requestLine.protocolEnd);
		// 校验请求行
		if (method.length() < 1) {
			throw new ServletException("Missing HTTP request method");
		} else if (requestLine.uriEnd < 1) {
			throw new ServletException("Missing HTTP request URI");
		}
		// 解析请求uri中的查询参数
		int question = requestLine.indexOf("?");
		if (question >= 0) {
			request.setQueryString(new String(requestLine.uri, question + 1,
					requestLine.uriEnd - question - 1));
			uri = new String(requestLine.uri, 0, question);
		} else {
			request.setQueryString(null);
			uri = new String(requestLine.uri, 0, requestLine.uriEnd);
		}
		// Checking for an absolute URI (with the HTTP protocol)
		// 大多数uri指向一个相对的资源，uri也可以是一个绝对值。http://www.brainysoftware.com/index.html?name=Tarzan
		if (!uri.startsWith("/")) {
			int pos = uri.indexOf("://");
			// Parsing out protocol and host name
			if (pos != -1) {
				pos = uri.indexOf('/', pos + 3);
				if (pos == -1) {
					uri = "";
				} else {
					uri = uri.substring(pos);
				}
			}
		}
		// Parse any requested session ID out of the request URI
		// 查询字符串中也可以包含一个会话标识，用jsessionid参数名来指代。
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
			// 当jsessionid被找到，意味着会话标识是携带在查询字符串里边的，而不是cookie中。因此传递true，否则传递false
			request.setRequestedSessionURL(true);
			uri = uri.substring(0, semicolon) + rest;
		} else {
			request.setRequestedSessionId(null);
			request.setRequestedSessionURL(false);
		}
		// Normalize URI (using String operations at the moment)
		String normalizedUri = normalize(uri);
		// Set the corresponding request properties
		((HttpRequest) request).setMethod(method);
		request.setProtocol(protocol);
		if (normalizedUri != null) {
			((HttpRequest) request).setRequestURI(normalizedUri);
		} else {
			((HttpRequest) request).setRequestURI(uri);
		}
		if (normalizedUri == null) {
			throw new ServletException("Invalid URI: " + uri + "'");
		}
	}

	/*
	 * 用于纠正“异常”的 URI.假如 uri 是正确的格式或者异常可以给纠正的话，normalize 将会返回相同的或者被纠正后的 URI。 假如
	 * URI 不能纠正的话，它将会给认为是非法的并且通常会返回null
	 */
	private String normalize(String uri) {
		return uri;
	}
}
