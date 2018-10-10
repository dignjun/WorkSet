package ex10.pyrmont.startup;

import org.apache.catalina.Connector;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Realm;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.loader.WebappLoader;

import ex10.pyrmont.core.SimpleContextConfig;
import ex10.pyrmont.core.SimpleRealm;
import ex10.pyrmont.core.SimpleWrapper;

@SuppressWarnings("deprecation")
public final class BootStrap1 {
	public static void main(String[] args) {
		System.setProperty("catalina.base", System.getProperty("user.dir"));
		Connector connector = new HttpConnector();
		SimpleWrapper wrapper1 = new SimpleWrapper();
		wrapper1.setName("Primitive");
		wrapper1.setServletClass("PrimitiveServlet");
		SimpleWrapper wrapper2 = new SimpleWrapper();
		wrapper2.setName("Modern");
		wrapper2.setServletClass("ModernServlet");

		StandardContext context = new StandardContext();
		context.setPath("/myApp");
		context.setDocBase("myApp");
		LifecycleListener listener = new SimpleContextConfig();
		// 这里明明有这个添加方法，为什么还需要向上转型为Lifecycle接口呢
		context.addLifecycleListener(listener);

		context.addChild(wrapper1);
		context.addChild(wrapper2);

		// add Valve

		Loader loader = new WebappLoader();
		context.setLoader(loader);

		context.addServletMapping("/Primitive", "Primitive");
		context.addServletMapping("/Modern", "Modern");

		// add ContextConfig

		SecurityCollection securityCollection = new SecurityCollection();
		securityCollection.addMethod("GET");
		securityCollection.addPattern("/");

		SecurityConstraint constraint = new SecurityConstraint();
		constraint.addCollection(securityCollection);
		constraint.addAuthRole("manager");
		LoginConfig loginConfig = new LoginConfig();
		loginConfig.setRealmName("Simple Realm");
		Realm realm = new SimpleRealm();

		context.setRealm(realm);
		context.addConstraint(constraint);
		context.setLoginConfig(loginConfig);

		connector.setContainer(context);

		try {
			connector.initialize();
			// 隔代遗传效应，父类/接口没有实现某个接口，而子类/实现实现了这个接口，而在程序中使用的多态的引用，所以需要强转调用子类实现的接口实现
			((Lifecycle) connector).start();
			((Lifecycle) context).start();

			// make the application wait until press a key
			System.in.read();
			((Lifecycle) context).stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
