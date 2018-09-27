package ex06.pyrmont.core;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.net.URLStreamHandler;

import org.apache.catalina.Container;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;

/**
 * 加载器，加载servlet
 * 
 * @author DINGJUN
 *
 */
public class SimpleLoader implements Loader, Lifecycle{

	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

	ClassLoader classLoader = null;
	Container container = null;
	
	// 构造
	public SimpleLoader(){
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(WEB_ROOT);
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
			urls[0] = new URL(null, repository, streamHandler);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	// Lifecycle的接口实现
	public void addLifecycleListener(LifecycleListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public LifecycleListener[] findLifecycleListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeLifecycleListener(LifecycleListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void start() throws LifecycleException {
		System.out.println("Starting SimpleLoader");
	}

	public void stop() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addRepository(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public String[] findRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

	// Loader接口实现
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public Container getContainer() {
		return container;
	}

	public DefaultContext getDefaultContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getDelegate() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getInfo() {
		return "A simple loader";
	}

	public boolean getReloadable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean modified() {
		// TODO Auto-generated method stub
		return false;
	}

	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setContainer(Container arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setDefaultContext(DefaultContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setDelegate(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setReloadable(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

}
