package ex032.pyrmont.connector.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.CookieTools;

/*
 * 响应
 */
@SuppressWarnings("rawtypes")
public class HttpResponse implements HttpServletResponse {

	private static final int BUFFER_SIZE = 1024;
	HttpRequest request;
	OutputStream output;
	PrintWriter writer;
	protected byte[] buffer = new byte[BUFFER_SIZE];
	protected int bufferCount = 0;

	// 响应是否已经提交
	protected boolean committed = false;
	// 响应输出的实际字节数
	protected int contentCount = 0;
	// 响应内容的长度
	protected int contentLength = -1;
	protected String contentType = null;
	// 响应的编码
	protected String encoding = null;
	// 响应中的cookies集合
	protected ArrayList cookies = new ArrayList();
	// 通过addHeader()方法来明确HTTP请求头类型，但是不包括通过setContentLength(), setContentType(),
	// 等等方法添加的值。
	// key是header的名字，值是一个被设置的ArrayList
	protected HashMap headers = new HashMap();
	protected final SimpleDateFormat format = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

	protected String message = getStatusMessage(HttpServletResponse.SC_OK);
	protected int status = HttpServletResponse.SC_OK;

	// 通过输出流来构造
	public HttpResponse(OutputStream output) {
		this.output = output;
	}

	public void finishResponse() {
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}

	public int getContentLength() {
		return contentLength;
	}

	protected String getProtocol() {
		return request.getProtocol();
	}

	protected String getStatusMessage(int status) {
		switch (status) {
		case SC_OK:
			return ("OK");
		case SC_ACCEPTED:
			return ("Accepted");
		case SC_BAD_GATEWAY:
			return ("Bad Gateway");
		case SC_BAD_REQUEST:
			return ("Bad Request");
		case SC_CONFLICT:
			return ("Conflict");
		case SC_CONTINUE:
			return ("Continue");
		case SC_CREATED:
			return ("Created");
		case SC_EXPECTATION_FAILED:
			return ("Expectation Failed");
		case SC_FORBIDDEN:
			return ("Forbidden");
		case SC_GATEWAY_TIMEOUT:
			return ("Gateway Timeout");
		case SC_GONE:
			return ("Gone");
		case SC_HTTP_VERSION_NOT_SUPPORTED:
			return ("HTTP Version Not Supported");
		case SC_INTERNAL_SERVER_ERROR:
			return ("Internal Server Error");
		case SC_LENGTH_REQUIRED:
			return ("Length Required");
		case SC_METHOD_NOT_ALLOWED:
			return ("Method Not Allowed");
		case SC_MOVED_PERMANENTLY:
			return ("Moved Permanently");
		case SC_MOVED_TEMPORARILY:
			return ("Moved Temporarily");
		case SC_MULTIPLE_CHOICES:
			return ("Multiple Choices");
		case SC_NO_CONTENT:
			return ("No Content");
		case SC_NON_AUTHORITATIVE_INFORMATION:
			return ("Non-Authoritative Information");
		case SC_NOT_ACCEPTABLE:
			return ("Not Acceptable");
		case SC_NOT_FOUND:
			return ("Not Found");
		case SC_NOT_IMPLEMENTED:
			return ("Not Implemented");
		case SC_NOT_MODIFIED:
			return ("Not Modified");
		case SC_PARTIAL_CONTENT:
			return ("Partial Content");
		case SC_PAYMENT_REQUIRED:
			return ("Payment Required");
		case SC_PRECONDITION_FAILED:
			return ("Precondition Failed");
		case SC_PROXY_AUTHENTICATION_REQUIRED:
			return ("Proxy Authentication Required");
		case SC_REQUEST_ENTITY_TOO_LARGE:
			return ("Request Entity Too Large");
		case SC_REQUEST_TIMEOUT:
			return ("Request Timeout");
		case SC_REQUEST_URI_TOO_LONG:
			return ("Request URI Too Long");
		case SC_REQUESTED_RANGE_NOT_SATISFIABLE:
			return ("Requested Range Not Satisfiable");
		case SC_RESET_CONTENT:
			return ("Reset Content");
		case SC_SEE_OTHER:
			return ("See Other");
		case SC_SERVICE_UNAVAILABLE:
			return ("Service Unavailable");
		case SC_SWITCHING_PROTOCOLS:
			return ("Switching Protocols");
		case SC_UNAUTHORIZED:
			return ("Unauthorized");
		case SC_UNSUPPORTED_MEDIA_TYPE:
			return ("Unsupported Media Type");
		case SC_USE_PROXY:
			return ("Use Proxy");
		case 207: // WebDAV
			return ("Multi-Status");
		case 422: // WebDAV
			return ("Unprocessable Entity");
		case 423: // WebDAV
			return ("Locked");
		case 507: // WebDAV
			return ("Insufficient Storage");
		default:
			return ("HTTP Response Status " + status);
		}
	}

	public OutputStream getStream() {
		return this.output;
	}

	// 发送http的请求头，如果还没有发送过
	@SuppressWarnings("deprecation")
	protected void sendHeaders() throws IOException {
		if (isCommitted()) {
			return;
		}

		// 准备一个适当的输出
		OutputStreamWriter osr = null;
		try {
			osr = new OutputStreamWriter(getStream(), getCharacterEncoding());
		} catch (Exception e) {
			osr = new OutputStreamWriter(getStream());
		}
		final PrintWriter outputWriter = new PrintWriter(osr);
		// 发送“Status:”header
		outputWriter.print(this.getProtocol());
		outputWriter.print(" ");
		outputWriter.print(status);
		if (message != null) {
			outputWriter.print(" ");
			outputWriter.print(message);
		}
		outputWriter.print("\r\n");

		// 发送content-length和content-type的头
		if (getContentType() != null) {
			outputWriter.print("Content-Type:" + getContentType() + "\r\n");
		}
		if (getContentLength() >= 0) {
			outputWriter.print("Content-Length:" + getContentLength() + "\r\n");
		}

		// 发送所有的头信息
		synchronized (headers) {
			Iterator names = headers.keySet().iterator();
			while (names.hasNext()) {
				String name = (String) names.next();
				ArrayList values = (ArrayList) headers.get(name);
				Iterator items = values.iterator();
				while (items.hasNext()) {
					String value = (String) items.next();
					outputWriter.print(name);
					outputWriter.print(": ");
					outputWriter.print(value);
					outputWriter.print("\r\n");
				}
			}
		}
		// Add the session ID cookie if necessary
		/*
		 * HttpServletRequest hreq = (HttpServletRequest) request.getRequest();
		 * HttpSession session = hreq.getSession(false); if ((session != null)
		 * && session.isNew() && (getContext() != null) &&
		 * getContext().getCookies()) { Cookie cookie = new Cookie("JSESSIONID",
		 * session.getId()); cookie.setMaxAge(-1); String contextPath = null; if
		 * (context != null) contextPath = context.getPath(); if ((contextPath
		 * != null) && (contextPath.length() > 0)) cookie.setPath(contextPath);
		 * else
		 * 
		 * cookie.setPath("/"); if (hreq.isSecure()) cookie.setSecure(true);
		 * addCookie(cookie); }
		 */
		// Send all specified cookies (if any)
		synchronized (cookies) {
			Iterator items = cookies.iterator();
			while (items.hasNext()) {
				Cookie cookie = (Cookie) items.next();
				outputWriter.print(CookieTools.getCookieHeaderName(cookie));
				outputWriter.print(": ");
				outputWriter.print(CookieTools.getCookieHeaderValue(cookie));
				outputWriter.print("\r\n");
			}
		}

		// Send a terminating blank line to mark the end of the headers
		outputWriter.print("\r\n");
		outputWriter.flush();

		committed = true;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}
	
	

	/* 接口实现的方法 */
	public String getCharacterEncoding() {
		return encoding;
	}

	public String getContentType() {
		return contentType;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCharacterEncoding(String charset) {
		// TODO Auto-generated method stub

	}

	public void setContentLength(int len) {
		// TODO Auto-generated method stub

	}

	public void setContentType(String type) {
		// TODO Auto-generated method stub

	}

	public void setBufferSize(int size) {
		// TODO Auto-generated method stub

	}

	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub

	}

	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public void setLocale(Locale loc) {
		// TODO Auto-generated method stub

	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addCookie(Cookie cookie) {
		// TODO Auto-generated method stub

	}

	public boolean containsHeader(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public String encodeURL(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeRedirectURL(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeRedirectUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendError(int sc, String msg) throws IOException {
		// TODO Auto-generated method stub

	}

	public void sendError(int sc) throws IOException {
		// TODO Auto-generated method stub

	}

	public void sendRedirect(String location) throws IOException {
		// TODO Auto-generated method stub

	}

	public void setDateHeader(String name, long date) {
		// TODO Auto-generated method stub

	}

	public void addDateHeader(String name, long date) {
		// TODO Auto-generated method stub

	}

	public void setHeader(String name, String value) {
		// TODO Auto-generated method stub

	}

	public void addHeader(String name, String value) {
		// TODO Auto-generated method stub

	}

	public void setIntHeader(String name, int value) {
		// TODO Auto-generated method stub

	}

	public void addIntHeader(String name, int value) {
		// TODO Auto-generated method stub

	}

	public void setStatus(int sc) {
		// TODO Auto-generated method stub

	}

	public void setStatus(int sc, String sm) {
		// TODO Auto-generated method stub

	}

}
