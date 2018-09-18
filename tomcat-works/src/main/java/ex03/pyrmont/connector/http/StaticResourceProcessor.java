package ex03.pyrmont.connector.http;

import java.io.IOException;
/**
 * 用来提供静态资源请求。
 * 
 * @author DINGJUN
 *
 */
public class StaticResourceProcessor {

	public void process(HttpRequest request, HttpResponse response) {
		try {
			response.sendStaticResource();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
