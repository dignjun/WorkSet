package ex03.pyrmont.connector.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import ex02.pyrmont.Constants;
import ex03.pyrmont.connector.ResponseStream;
import ex03.pyrmont.connector.ResponseWriter;

/**
 * HttpResponse实现HttpServletResponse接口
 * 
 * @author DINGJUN
 *
 */
@SuppressWarnings("unused")
public class HttpResponse implements HttpServletResponse{

	private static final int BUFFER_SIZE = 1024;
	protected int bufferCount = 0;
	protected byte[] buffer = new byte[BUFFER_SIZE];
	protected int contentCount = 0;
	
	private OutputStream outputStream;
	private HttpRequest httpRequest;
	private PrintWriter writer;
	
	// http响应的头部
	@SuppressWarnings("rawtypes")
	protected HashMap headers = new HashMap();
	public HttpResponse(OutputStream outputStream){
		this.outputStream = outputStream;
	}
	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			/* request.getUri 已经被 request.getRequestURI 取代 */
//			File file = new File(Constants.WEB_ROOT, httpRequest.getUri());
			// TODO 路径完全没有错，但是下面输出为啥没有东西？？使用outputstream输出html到页面无显示，但是控制台的打印输出内容是没有问题的。
//			System.out.println(file.getAbsolutePath());
//			fis = new FileInputStream(file);
			/*
			 * HTTP Response = Status-Line(( general-header | response-header |
			 * entity-header ) CRLF) CRLF [ message-body ] Status-Line =
			 * HTTP-Version SP Status-Code SP Reason-Phrase CRLF
			 */
//			int ch = fis.read(bytes, 0, BUFFER_SIZE);
//			while (ch != -1) {
//				outputStream.write(bytes, 0, ch);
//				ch = fis.read(bytes, 0, BUFFER_SIZE);
//			}
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
					+ "Content-Type: text/html\r\n" + "Content-Length: 23\r\n"
					+ "\r\n" + "<h1>[Display the correct display from the failed page.]</h1>";
			outputStream.write(errorMessage.getBytes());
		} catch (FileNotFoundException e) {
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
					+ "Content-Type: text/html\r\n" + "Content-Length: 51\r\n"
					+ "\r\n" + "<h1>File Not Found</h1>";
			outputStream.write(errorMessage.getBytes());
		} finally {
			if (fis != null)
				fis.close();
		}
	}
	
	public void write(int b) throws IOException {
	    if (bufferCount >= buffer.length)
	      flushBuffer();
	    buffer[bufferCount++] = (byte) b;
	    contentCount++;
	  }

	  public void write(byte b[]) throws IOException {
	    write(b, 0, b.length);
	  }

	  public void write(byte b[], int off, int len) throws IOException {
	    // If the whole thing fits in the buffer, just put it there
	    if (len == 0)
	      return;
	    if (len <= (buffer.length - bufferCount)) {
	      System.arraycopy(b, off, buffer, bufferCount, len);
	      bufferCount += len;
	      contentCount += len;
	      return;
	    }

	    // Flush the buffer and start writing full-buffer-size chunks
	    flushBuffer();
	    int iterations = len / buffer.length;
	    int leftoverStart = iterations * buffer.length;
	    int leftoverLen = len - leftoverStart;
	    for (int i = 0; i < iterations; i++)
	      write(b, off + (i * buffer.length), buffer.length);

	    // Write the remainder (guaranteed to fit in the buffer)
	    if (leftoverLen > 0)
	      write(b, off + leftoverStart, leftoverLen);
	  }
	
	
	public void setHttpRequest(HttpRequest httpRequest){
		this.httpRequest = httpRequest;
	}
	@SuppressWarnings("unchecked")
	public void setHeaders(String key, String value){
		headers.put(key, value);
	}
	/*---------实现的方法---------*/
	public String getCharacterEncoding() {
		return null;
	}
	public String getContentType() {
		return null;
	}
	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}
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
	}
	public void setContentType(String type) {
	}
	public void setBufferSize(int size) {
	}
	public int getBufferSize() {
		return 0;
	}
	public void flushBuffer() throws IOException {
	}
	public void resetBuffer() {
	}
	public boolean isCommitted() {
		return false;
	}
	public void reset() {
	}
	public void setLocale(Locale loc) {
	}
	public Locale getLocale() {
		return null;
	}
	public void addCookie(Cookie cookie) {
	}
	public boolean containsHeader(String name) {
		return false;
	}
	public String encodeURL(String url) {
		return null;
	}
	public String encodeRedirectURL(String url) {
		return null;
	}
	public String encodeUrl(String url) {
		return null;
	}
	public String encodeRedirectUrl(String url) {
		return null;
	}
	public void sendError(int sc, String msg) throws IOException {
	}
	public void sendError(int sc) throws IOException {
	}
	public void sendRedirect(String location) throws IOException {
	}
	public void setDateHeader(String name, long date) {
	}
	public void addDateHeader(String name, long date) {
	}
	public void setHeader(String name, String value) {
	}
	public void addHeader(String name, String value) {
	}
	public void setIntHeader(String name, int value) {
	}
	public void addIntHeader(String name, int value) {
	}
	public void setStatus(int sc) {
	}
	public void setStatus(int sc, String sm) {
	}
}
