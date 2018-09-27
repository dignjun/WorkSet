package ex06.pyrmont.core;

import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.InstanceListener;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Mapper;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.util.LifecycleSupport;

@SuppressWarnings("rawtypes")
public class SimpleWrapper implements Wrapper, Pipeline, Lifecycle{

	// the servlet instance
	private Servlet instance = null;
	private String servletClass;
	private Loader loader;
	private String name;
	protected LifecycleSupport lifecycle = new LifecycleSupport(this);
	protected SimplePipeline pipeline = new SimplePipeline(this);
	protected Container parent = null;
	protected boolean started = false;
	
	public SimpleWrapper(){
		pipeline.setBasic(new SimpleWrapperValve());
	}
	
	public void addChild(Container arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addContainerListener(ContainerListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addMapper(Mapper arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public Container findChild(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Container[] findChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	public ContainerListener[] findContainerListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public Mapper findMapper(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Mapper[] findMappers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cluster getCluster() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Loader getLoader() {
		if(loader != null){
			return loader;
		}
		if(parent != null){
			return parent.getLoader();
		}
		return null;
	}

	public Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	public Manager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	public Container getParent() {
		return parent;
	}

	public ClassLoader getParentClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	public Realm getRealm() {
		// TODO Auto-generated method stub
		return null;
	}

	public DirContext getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		pipeline.invoke(request, response);
	}

	public Container map(Request arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeChild(Container arg0) {
		// TODO Auto-generated method stub
		
	}

	public void removeContainerListener(ContainerListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void removeMapper(Mapper arg0) {
		// TODO Auto-generated method stub
		
	}

	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setCluster(Cluster arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	public void setLogger(Logger arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setManager(Manager arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Container container) {
		parent = container;
	}

	public void setParentClassLoader(ClassLoader arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setRealm(Realm arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setResources(DirContext arg0) {
		// TODO Auto-generated method stub
		
	}

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

	// 声明周期的启动接口实现
	public synchronized void start() throws LifecycleException {
		System.out.println("Starting Wrapper:" + name);
		if(started){
			throw new LifecycleException("Wrapper already started");
		}
		
		// Notify our interested LifecycleListeners
		// 通知感兴趣的监听器，有点观察者模式意思
		lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
		started = true;
		
		// Start our subordinate comonents, if any
		if((loader != null)&&(loader instanceof Lifecycle))
			// 这里的转型没道理啊???其实这个loader也是一个顶级接口，我们会使用它的实现子类SimpleLoader类，这个类是实现了Lifecycle接口的，所以转型是不存在问题的。
			((Lifecycle) loader).start();
		
		// Start the Valves in our pipeline (including the basic), if any
		if(pipeline instanceof Lifecycle)
			((Lifecycle)pipeline).start();
		
		// Notify our interested LifecycleListeners
		lifecycle.fireLifecycleEvent(START_EVENT, null);
		// Notify our interested LifecycleListeners
		lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
	}

	public void stop() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	// 容器添加阀门，其实是委托给关联的流水线进行添加的，而流水线每添加一个阀门就会重新创建一个阀门数组
	public synchronized void addValve(Valve valve) {
		pipeline.addValve(valve);
	}

	public Valve getBasic() {
		return pipeline.getBasic();
	}

	public Valve[] getValves() {
		return pipeline.getValves();
	}

	public void removeValve(Valve valve) {
		pipeline.removeValve(valve);
	}

	public void setBasic(Valve valve) {
		pipeline.setBasic(valve);
	}

	public void addInitParameter(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addInstanceListener(InstanceListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addSecurityReference(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public Servlet allocate() throws ServletException {
		// Load and initialize our instance if necessary
		if(instance == null){
			try {
				instance = loadServlet();
			} catch (ServletException e) {
				throw e;
			} catch (Throwable e) {
				throw new ServletException("Cannot allocate a servlet instance",e);
			}
		}
		return instance;
	}
	
	public Servlet loadServlet() throws ServletException{
		if(instance != null)
			return instance;
		
		Servlet servlet = null;
		String actualClass = servletClass;
		if(actualClass == null){
			throw new ServletException("servlet calss has not been specified");
		}
		
		Loader loader = getLoader();
		// Acquire an instance of the class loader to be used
		if(loader == null){
			throw new ServletException("No loader");
		}
		ClassLoader classLoader = loader.getClassLoader();
		
		// Load the specified servlet class from the appropriate class loader
		Class classClass = null;
		try {
			if(classLoader != null){
				classClass = classLoader.loadClass(actualClass);
			}
		} catch (ClassNotFoundException e) {
			throw new ServletException("Servlet class not found");
		}
		
		// Instantiate and initalize an instance of the servlet class itself
		try {
			servlet = (Servlet) classClass.newInstance();
		} catch (Throwable e) {
			throw new ServletException("Failed to instantiate servlet");
		}
		
		// Call the initialization method of this servlet
		try {
			servlet.init(null);
		} catch (Exception e) {
			throw new ServletException("Failed initialize servlet.");
		}
		
		return servlet;
	}

	public void deallocate(Servlet arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public String findInitParameter(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findInitParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public String findSecurityReference(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findSecurityReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getAvailable() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getJspFile() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLoadOnStartup() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getRunAs() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServletClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isUnavailable() {
		// TODO Auto-generated method stub
		return false;
	}

	public void load() throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void removeInitParameter(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void removeInstanceListener(InstanceListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void removeSecurityReference(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setAvailable(long arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setJspFile(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setLoadOnStartup(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setRunAs(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}

	public void unavailable(UnavailableException arg0) {
		// TODO Auto-generated method stub
		
	}

	public void unload() throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
