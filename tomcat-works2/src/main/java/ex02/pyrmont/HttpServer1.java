package ex02.pyrmont;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer1 {
	public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	public static void main(String[] args) {
		HttpServer1 server = new HttpServer1();
		server.await();
	}
	public void await(){
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				// create request object and parse
				Request request = new Request(input);
				request.parse();
				// create response object,response object contains request object
				Response response = new Response(output);
				response.setRequest(request);
				// check if this is a servlet request or static resource
				// a request for a servlet begins with "/servlet/"
				if (request.getUri().startsWith("/servlet")) {
					// servlet processor
				}else{
					// static resource processor
				}
				
				// close socket
				socket.close();
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}

























