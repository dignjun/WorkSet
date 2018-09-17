package ex02.pyrmont;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 简单的服务器应用程序类，相对一ex01版的serve，它可以同时处理静态资源和servlet
 * 请求一个静态资源：http://machineName:port/staticResource
 * 请求一个servlet：http://machineName:port/servlet/servletClass
 * 
 * 
 * @author DINGJUN
 *
 */
public class HttpServer1 {

	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	private boolean shutdown = false;

	// 应用程序容器启动
	public static void main(String[] args) {
		HttpServer1 server = new HttpServer1();
		server.await();
	}

	public void await() {
		// 构建一个socket的server应用。
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1,
					InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// Loop waiting for a request，循环等待请求
		while (!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				socket.getInputStream();
				output = socket.getOutputStream();
				// 接收到请求，创建一个request对象，并且解析这个请求
				Request request = new Request(input);
				request.parse();
				// 创建一个响应的对象
				Response response = new Response(output);
				response.setRequest(request);
				/**
				 * 判断这个请求是一个servlet请求还是一个静态页面的请求
				 * 如果使用一个servlet请求的话，那么请求的uri中要存在/servlet/字符
				 */
				if (request.getUri().startsWith("/servlet/")) {
					ServletProcessor1 processor = new ServletProcessor1();
					processor.process(request, response);
				} else {
					StaticResourceProcessor processor = new StaticResourceProcessor();
					processor.process(request, response);
				}
				// 关闭这个socket
				socket.close();
				// 检查之前的uri是否是关闭的请求。
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
