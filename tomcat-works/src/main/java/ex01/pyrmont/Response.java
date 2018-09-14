package ex01.pyrmont;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {

	private static final int BUFFER_SIZE = 256;
	Request request;
	OutputStream output;

	public Response(OutputStream output) {
		this.output = output;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			File file = new File(HttpServer.WEB_ROOT, request.getUri());
			if (file.exists()) {
//				-------------------------------------
				fis = new FileInputStream(file);
				int ch = fis.read(bytes, 0, BUFFER_SIZE);
				StringBuffer sb = new StringBuffer();
				// 这个页面是怎么写都是输出错误，搞不明白，如果这里给出正确的200相应的请求头应该就是正确的，尝试了一下，貌似还是不行的。
				// 不仅读取一个html显示不出来，而且即使是下面的内容输出到页面上，如果是中文的话，乱码不成样子QAQ。
				while (ch != -1) {
					sb.append(new String(bytes));
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
				String result = "HTTP/1.1 404 \r\n"
						+ "Content-Type: text/html\r\n" + "Content-Length: "
						+ "<h3>the first web serve start succeed!</h3>".length() + "\r\n" + "\r\n"
						+ "<h3>the first web serve start succeed!</h3>";// 内容如果是中文，乱码，而且如果这个content-length如果不对，也不能正确显示。
				System.out.println(result);
				output.write(result.getBytes());
//				-------------------------------------
				// 原版，这种写法的页面输出到浏览器一直错误，使用eclipse建立的html模版
				// 请注意，这种情况下，静态资源是作为原始数据发送给浏览器的。
//				fis = new FileInputStream(file);
//				int ch = fis.read(bytes, 0, BUFFER_SIZE);
//				while (ch != -1) {
//					output.write(bytes, 0, ch);
//					ch = fis.read(bytes, 0, BUFFER_SIZE);
//				}
//				-------------------------------------
			} else {
				// file not found
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
						+ "Content-Type: text/html\r\n"
						+ "Content-Length: 23\r\n" + "\r\n"
						+ "<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			// thrown if cannot instantiate a File object
			System.out.println(e.toString());
		} finally {
			if (fis != null)
				fis.close();
		}
	}
}
