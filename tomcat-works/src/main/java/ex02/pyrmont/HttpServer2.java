package ex02.pyrmont;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 修改后的类
 * 
 * @author DINGJUN
 *
 */
public class HttpServer2 {

	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	private boolean shutdown = false;

	// 应用程序容器启动
	public static void main(String[] args) {
		HttpServer2 server = new HttpServer2();
		server.await();
	}

	public void await() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1,
					InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
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
				if (request.getUri().startsWith("/servlet/")) {
					/*修改的地方*/
					ServletProcessor2 processor = new ServletProcessor2();
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
