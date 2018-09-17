package ex03.pyrmont.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * 
 * 连接器，连接器和容器的关键组件之一。
 * 职责是创建一个服务器套接字用来等待钱来的HTTP请求。
 * 这个类实现了Runnable接口，所以它能被它自己的线程专用？
 * 当启动应用程序，一个HttpConnector的实例被创建，并且它的run方法被执行。这里的实现相当nice！！！。
 * 做了下面三件事：
 * 等待HTTP请求
 * 为每个请求创建一个HttpProcessor实例
 * 调用HttpProcessor的process方法
 * 
 * @author DINGJUN
 *
 */
public class HttpConnector implements Runnable{

	boolean stopped;
	private String scheme = "http";
	
	public String getScheme() {
		return scheme;
	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("未知域名异常！");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while(!stopped) {
			// 接收即将到来的连接服务
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				continue;
			}
			// Hand this socket off to an HttpProcessor
//			HttpProcessor processor = new HttpProcessor(this);
//			processor.process(socket);
		}
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
}

























