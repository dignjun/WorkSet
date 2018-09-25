package ex032.pyrmont.connector.http;

import java.io.IOException;

public class StaticResourceProcessor {
	
	public void process(HttpRequest request, HttpResponse response) {
		try {
			response.sendStaticResource();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
