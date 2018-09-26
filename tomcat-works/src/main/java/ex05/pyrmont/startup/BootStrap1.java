package ex05.pyrmont.startup;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.http.HttpConnector;

import ex05.pyrmont.core.SimpleLoader;
import ex05.pyrmont.core.SimpleWrapper;
import ex05.pyrmont.valves.ClientIPLoggerValve;
import ex05.pyrmont.valves.HeaderLoggerValve;

/**
 * 启动类
 * 
 * @author DINGJUN
 *
 */
@SuppressWarnings("deprecation")
public class BootStrap1 {

	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector();
		SimpleWrapper wrapper = new SimpleWrapper();
		wrapper.setServletClass("ModernServlet");
		SimpleLoader loader = new SimpleLoader();
		Valve valve1 = new HeaderLoggerValve();
		Valve valve2 = new ClientIPLoggerValve();
		wrapper.setLoader(loader);
		wrapper.addValve(valve1);
		wrapper.addValve(valve2);
		
		connector.setContainer(wrapper);
		
		try {
			connector.initialize();
			connector.start();
			
			// make the application wait until we press a key
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
