package ex05.pyrmont.core;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import org.apache.catalina.Container;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Loader;

/**
 * Servlet的加载任务。
 * 
 * @author DINGJUN
 *
 */
public class SimpleLoader implements Loader{

	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
	
	ClassLoader classLoader = null;
	Container container = null;
	
	// 构造创建这个类加载器，通过getter方法获取。
	public SimpleLoader(){
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(WEB_ROOT);
			String repository = (new URL("file",null,classPath.getCanonicalPath() + File.separator)).toString();
			urls[0] = new URL(null, repository,streamHandler);
			classLoader = new URLClassLoader(urls);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		
	}

	public void addRepository(String arg0) {
		
	}

	public String[] findRepositories() {
		return null;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public Container getContainer() {
		return container;
	}

	public DefaultContext getDefaultContext() {
		return null;
	}

	public boolean getDelegate() {
		return false;
	}

	public String getInfo() {
		return "A simple loader";
	}

	public boolean getReloadable() {
		return false;
	}

	public boolean modified() {
		return false;
	}

	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		
	}

	public void setContainer(Container arg0) {
		
	}

	public void setDefaultContext(DefaultContext arg0) {
		
	}

	public void setDelegate(boolean arg0) {
		
	}

	public void setReloadable(boolean arg0) {
		
	}

}
