package ex02.pyrmont;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 * 响应类，在sever socket接收到请求时创建，用于响应给浏览器
 * 
 * @author DINGJUN
 *
 */
public class Response implements ServletResponse {

	private static final int BUFFER_SIZE = 1024;
	Request request;
	OutputStream output;
	PrintWriter writer;

	// 构造时，拿到响应使用的输出流，通过socket获得的outpustream
	public Response(OutputStream output) {
		this.output = output;
	}

	// 在获取uri的时候需要使用到这个对象，或许使用一个string变量记录这个也是可以的，通过构造或者set方法获取，不过这里通过持有request的引用实现。
	public void setRequest(Request request) {
		this.request = request;
	}

	/* 这个方法用于静态页面的响应  */
	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			/* request.getUri 已经被 request.getRequestURI 取代 */
			File file = new File(Constants.WEB_ROOT, request.getUri());
			// TODO 路径完全没有错，但是下面输出为啥没有东西？？使用outputstream输出html到页面无显示，但是控制台的打印输出内容是没有问题的。
			System.out.println(file.getAbsolutePath());
			fis = new FileInputStream(file);
			/*
			 * HTTP Response = Status-Line(( general-header | response-header |
			 * entity-header ) CRLF) CRLF [ message-body ] Status-Line =
			 * HTTP-Version SP Status-Code SP Reason-Phrase CRLF
			 */
			int ch = fis.read(bytes, 0, BUFFER_SIZE);
			while (ch != -1) {
				output.write(bytes, 0, ch);
				ch = fis.read(bytes, 0, BUFFER_SIZE);
			}
		} catch (FileNotFoundException e) {
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
					+ "Content-Type: text/html\r\n" + "Content-Length: 23\r\n"
					+ "\r\n" + "<h1>File Not Found</h1>";
			output.write(errorMessage.getBytes());
		} finally {
			if (fis != null)
				fis.close();
		}
	}

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
		// autoflush is true, println() will flush,
		// but print() will not.
		/*第二个参数表明是否允许自动刷新，传递true作为第二个参数将会使任何println方法的调用都会刷新输出，不过print方法不会刷新输出。
		       因此，任何print方法的调用都会发生在servlet的service方法的最后一行，输出将不会被发送到浏览器，这个缺点下个应用在修复*/
		writer = new PrintWriter(output, true);
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
}
