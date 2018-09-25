package ex032.pyrmont.connector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import ex032.pyrmont.connector.http.HttpProcessor;

/**
 * 连接器，职责是创建一个服务器套接字用来等待前来的 HTTP 请求
 * @author DINGJUN
 *
 */
public class HttpConnector implements Runnable{

	boolean stopped;
	private String scheme = "http";
	
	public String getScheme(){
		return scheme;
	}
	
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		while(!stopped){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				continue;
			}
			HttpProcessor httpProcessor = new HttpProcessor(this);
			httpProcessor.process(socket);
			
		}
	}
	
	public void start(){
		Thread thread = new Thread(this);
		thread.run();
	}

}
























