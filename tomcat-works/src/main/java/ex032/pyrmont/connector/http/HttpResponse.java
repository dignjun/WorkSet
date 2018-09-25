package ex032.pyrmont.connector.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.CookieTools;

import ex03.pyrmont.connector.ResponseWriter;
import ex032.pyrmont.connector.ResponseStream;

/*
 * 响应
 */
@SuppressWarnings({"rawtypes","unchecked"})
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
	
	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			File file = new File(Constants.WEB_ROOT, request.getRequestURI());
			fis = new FileInputStream(file);
			/*
	         HTTP Response = Status-Line
	           *(( general-header | response-header | entity-header ) CRLF)
	           CRLF
	           [ message-body ]
	         Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
			*/
			int ch = fis.read(bytes, 0, BUFFER_SIZE);
			while(ch != -1){
				output.write(bytes, 0, ch);
				ch = fis.read(bytes, 0, BUFFER_SIZE);
			}
		} catch (FileNotFoundException e) {
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
			        "Content-Type: text/html\r\n" +
			        "Content-Length: 23\r\n" +
			        "\r\n" +
			        "<h1>File Not Found</h1>";
			output.write(errorMessage.getBytes());
		} finally{
			if(fis != null){
				fis.close();
			}
		}
	}
	
	public void write(int b) throws IOException{
		if(bufferCount >= buffer.length){
			flushBuffer();
		}
		buffer[bufferCount ++] = (byte) b;
		contentCount ++;
	}
	public void write(byte b[], int off, int len) throws IOException{
		if(len == 0) {
			return;
		}
		if(len <= (buffer.length - bufferCount)) {
			System.arraycopy(b, off, buffer, bufferCount, len);
			bufferCount += len;
			contentCount += len;
			return;
		}
		
		flushBuffer();
		int iterations = len / buffer.length;
		int leftoverStart = iterations * buffer.length;
		int leftoverLen = len - leftoverStart;
		for(int i=0; i < iterations; i ++){
			write(b, off + (i * buffer.length), buffer.length);
		}
		if(leftoverLen > 0) {
			write(b, off + leftoverStart, leftoverLen);
		}
	}
	/* 接口实现的方法 */
	public String getCharacterEncoding() {
		if(encoding == null)
			return "ISO-8859-1";
		else
			return encoding;
	}

	public String getContentType() {
		return contentType;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	// 获取输出流
	public PrintWriter getWriter() throws IOException {
		ResponseStream newStream = new ResponseStream(this);
		newStream.setCommit(false);
		OutputStreamWriter osr = new OutputStreamWriter(newStream, getCharacterEncoding());
		writer = new ResponseWriter(osr);
		return writer;
	}

	public void setCharacterEncoding(String charset) {
	}

	public void setContentLength(int len) {
		if(isCommitted())
			return;
		this.contentLength = len;
	}

	public void setContentType(String type) {
	}

	public void setBufferSize(int size) {

	}

	public int getBufferSize() {
		return 0;
	}

	public void flushBuffer() throws IOException {
		// committed = true;
		if(bufferCount > 0){
			try {
				output.write(buffer, 0, bufferCount);
			} finally {
				bufferCount = 0;
			}
		}
	}

	public void resetBuffer() {

	}

	public boolean isCommitted() {
		return committed;
	}

	public void reset() {

	}

	public void setLocale(Locale loc) {
		if(isCommitted()){
			return;
		}
		String language = loc.getLanguage();
		if((language!=null)&&(language.length()>0)){
			String country = loc.getCountry();
			StringBuffer value = new StringBuffer(language);
			if((country != null) && (country.length() > 0)){
				value.append('-');
				value.append(country);
			}
			setHeader("Content-Language", value.toString());
		}
	}

	public Locale getLocale() {
		return null;
	}

	public void addCookie(Cookie cookie) {
		if(isCommitted()) {
			return ;
		}
		synchronized (cookies) {
			cookies.add(cookie);
		}
	}

	public boolean containsHeader(String name) {
		synchronized (headers) {
			return headers.get(name) != null;
		}
	}

	public String encodeURL(String url) {
		return null;
	}

	public String encodeRedirectURL(String url) {
		return null;
	}

	public String encodeUrl(String url) {
		return encodeURL(url);
	}

	public String encodeRedirectUrl(String url) {
		return encodeRedirectURL(url);
	}

	public void sendError(int sc, String msg) throws IOException {

	}

	public void sendError(int sc) throws IOException {

	}

	public void sendRedirect(String location) throws IOException {

	}

	public void setDateHeader(String name, long date) {
		if(isCommitted())
			return;
		setHeader(name, format.format(new Date(date)));
	}

	public void addDateHeader(String name, long date) {
		if(isCommitted())
			return;
		addHeader(name, format.format(new Date(date)));

	}

	// 设置响应头，注意和addHeader方法做出区分。
	public void setHeader(String name, String value) {
		if(committed)
			return;
		ArrayList values = new ArrayList();
		values.add(value);
		synchronized (headers) {
			headers.put(name, values);
		}
		String match = name.toLowerCase();
		if(match.equals("content-length")){
			int contentLength= -1;
			try {
				contentLength = Integer.parseInt(value);
			} catch (Exception e) {
				;
			}
			if(contentLength >= 0){
				setContentLength(contentLength);
			}else{
				setContentType(value);
			}
		}
	}

	// 添加响应头，这个头部是一个HashMap，键值对的值是一个ArrayList，第一次添加的时候先判断是否是已经存在过，然后添加值。
	public void addHeader(String name, String value) {
		if(isCommitted())
			return;
		synchronized (headers) {
			ArrayList values = (ArrayList) headers.get(name);
			if(values == null){
				values = new ArrayList();
				headers.put(name, values);
			}
			values.add(value);
		}
	}

	public void setIntHeader(String name, int value) {
		if(isCommitted()){
			return;
		}
		setHeader(name, "" + value);
	}

	public void addIntHeader(String name, int value) {
		if(isCommitted())
			return;
		addHeader(name, "" + value);
	}

	public void setStatus(int sc) {

	}

	public void setStatus(int sc, String sm) {

	}

}
