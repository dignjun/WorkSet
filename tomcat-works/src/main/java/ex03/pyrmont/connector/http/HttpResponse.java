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
			File file = new File(Constants.WEB_ROOT, httpRequest.getUri());
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
					+ "\r\n" + "<h1>从失败的页面展示正确的</h1>";
			outputStream.write(errorMessage.getBytes());
		} catch (FileNotFoundException e) {
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
					+ "Content-Type: text/html\r\n" + "Content-Length: 23\r\n"
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
	@Override
	public String getCharacterEncoding() {
		return null;
	}
	@Override
	public String getContentType() {
		return null;
	}
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}
	@Override
	public PrintWriter getWriter() throws IOException {
		ResponseStream newStream = new ResponseStream(this);
		newStream.setCommit(false);
		OutputStreamWriter osr = new OutputStreamWriter(newStream, getCharacterEncoding());
		writer = new ResponseWriter(osr);
		return writer;
	}
	@Override
	public void setCharacterEncoding(String charset) {
	}
	@Override
	public void setContentLength(int len) {
	}
	@Override
	public void setContentType(String type) {
	}
	@Override
	public void setBufferSize(int size) {
	}
	@Override
	public int getBufferSize() {
		return 0;
	}
	@Override
	public void flushBuffer() throws IOException {
	}
	@Override
	public void resetBuffer() {
	}
	@Override
	public boolean isCommitted() {
		return false;
	}
	@Override
	public void reset() {
	}
	@Override
	public void setLocale(Locale loc) {
	}
	@Override
	public Locale getLocale() {
		return null;
	}
	@Override
	public void addCookie(Cookie cookie) {
	}
	@Override
	public boolean containsHeader(String name) {
		return false;
	}
	@Override
	public String encodeURL(String url) {
		return null;
	}
	@Override
	public String encodeRedirectURL(String url) {
		return null;
	}
	@Override
	public String encodeUrl(String url) {
		return null;
	}
	@Override
	public String encodeRedirectUrl(String url) {
		return null;
	}
	@Override
	public void sendError(int sc, String msg) throws IOException {
	}
	@Override
	public void sendError(int sc) throws IOException {
	}
	@Override
	public void sendRedirect(String location) throws IOException {
	}
	@Override
	public void setDateHeader(String name, long date) {
	}
	@Override
	public void addDateHeader(String name, long date) {
	}
	@Override
	public void setHeader(String name, String value) {
	}
	@Override
	public void addHeader(String name, String value) {
	}
	@Override
	public void setIntHeader(String name, int value) {
	}
	@Override
	public void addIntHeader(String name, int value) {
	}
	@Override
	public void setStatus(int sc) {
	}
	@Override
	public void setStatus(int sc, String sm) {
	}
}
