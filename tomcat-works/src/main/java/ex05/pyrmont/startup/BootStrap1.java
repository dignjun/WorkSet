package ex05.pyrmont.startup;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.http.HttpConnector;

import ex05.pyrmont.core.SimpleLoader;
import ex05.pyrmont.core.SimpleWrapper;
import ex05.pyrmont.valves.ClientIPLoggerValve;
import ex05.pyrmont.valves.HeaderLoggerValve;

/**
 * 启动类
 * 这个服务只使用到一个Wrapper
 * @author DINGJUN
 *
 */
@SuppressWarnings("deprecation")
public class BootStrap1 {

	public static void main(String[] args) {
		// 连接器
		HttpConnector connector = new HttpConnector();
		// 容器，这里使用的是Wrapper容器。还有Engine,Host,Context
		SimpleWrapper wrapper = new SimpleWrapper();
		// 设置这个容器的名字
		wrapper.setServletClass("ModernServlet");
		// 类加载器
		SimpleLoader loader = new SimpleLoader();
		// 容器中处理的servlet的阀门过滤器
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
