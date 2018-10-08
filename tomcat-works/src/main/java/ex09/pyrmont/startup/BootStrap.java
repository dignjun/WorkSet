package ex09.pyrmont.startup;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.session.StandardManager;

import ex09.pyrmont.core.SimpleContextConfig;
import ex09.pyrmont.core.SimpleWrapper;

/**
 * 启动类 其实就是一个main方法，里面有一个while循环。 循环监听socket服务的特定端口，一旦有请求进来则创建一个servlet实例？其实
 * 
 * @author DINGJUN
 *
 */
@SuppressWarnings("deprecation")
public class BootStrap {
	public static void main(String[] args) {
		System.setProperty("catalina.base", System.getProperty("user.dir"));
		HttpConnector connector = new HttpConnector();
		SimpleWrapper wrapper1 = new SimpleWrapper();
		wrapper1.setName("Session");
		wrapper1.setServletClass("SessionServlet");

		Context context = new StandardContext();
		context.setPath("/myApp");
		context.setDocBase("myApp");

		context.addChild(wrapper1);

		// context.addServletMapping(pattern,name);
		// note that we must use /myApp/Session, not just /Session
		// because the /myApp section must be the same as the path, so the
		// cookie will
		// be sent back.
		context.addServletMapping("/myApp/Session", "Session");

		// add ContextConfig. This listener is important because it configures
		// StandardContext(sets configured to true).otherwis standardcontext
		// won't be start
		LifecycleListener listener = new SimpleContextConfig();
		// 这里的转型是需要留心的，因为context接口本身是没有实现Lifecycle接口的，但是context接口的实现
		((Lifecycle) context).addLifecycleListener(listener);

		// here is our loader
		Loader loader = new WebappLoader();
		// associate the loader with the context
		context.setLoader(loader);

		connector.setContainer(context);

		// add a manager
		StandardManager manager = new StandardManager();
		context.setManager(manager);

		try {
			connector.initialize();
			// 这个connector实现了Lifecycle接口，可以直接调用start方法的，这里为什么要进行向上转型呢？
			// 一般我们向上转型是因为我们不想使用子类的实现，所以向上转型使用父类的实现。不过这里是接口向上转型之后调用的方法还是某个具体的实现，目前看这个子类就是实现者，所以最终还是使用的connector方法的start方法。
			((Lifecycle) connector).start();
			((Lifecycle) context).start();

			// make the application wait until we press a key
			System.in.read();
			((Lifecycle) context).stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
