package ex07.pyrmont.core;

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

public class SimpleWrapper implements Wrapper, Pipeline, Lifecycle{

	private Servlet instance = null;
	private Loader loader = null;
	private String servletClass;
	private String name;
	
	protected LifecycleSupport lifecycle = new LifecycleSupport(this);
	private SimplePipeline pipeline = new SimplePipeline(this);
	protected Container parent = null;
	protected boolean started = false;

	// 构造中给出basic阀门处理请求
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

	// 获取加载器
	public Loader getLoader() {
		if(loader != null)
			return loader;
		// 子容器中没有加载器，那么查看父类是否有加载器
		if(parent != null)
			parent.getLoader();
		// 如果都没有则返回null
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

	// 生命周期的接口实现。
	public synchronized void start() throws LifecycleException {
		System.out.println("Starting Wrapper " + name);
		if(started)
			throw new LifecycleException("Wrapper already started");
		lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
		started = true;
		if((loader != null)&&(loader instanceof Lifecycle))
			((Lifecycle) loader).start();
		if(pipeline instanceof Lifecycle)
			((Lifecycle) pipeline).start();
		lifecycle.fireLifecycleEvent(START_EVENT, null);
		lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
	}

	// 生命周期停止的实现
	public void stop() throws LifecycleException {
		System.out.println("Stopping Wrapper " + name);
		// 多了一个servlet的销毁的过程。
		try {
			instance.destroy();
		} catch (Exception e) {
		}
		// 并且将servlet的引用置空。
		instance = null;
		if(!started)
			throw new LifecycleException("Wrapper" + name + "not started");
		lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);
		lifecycle.fireLifecycleEvent(STOP_EVENT, null);
		if(pipeline instanceof Lifecycle)
			((Lifecycle) pipeline).stop();
		if(loader instanceof Lifecycle)
			((Lifecycle)loader).stop();
		lifecycle.fireLifecycleEvent(AFTER_STOP_EVENT, null);
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
		if(instance == null){
			try {
				instance = loadServlet();
			} catch (ServletException e) {
				throw e;
			} catch (Throwable e) {
				throw new ServletException("Cannot allocate a servlet instance", e);
			}
		}
		return instance;
	}
	
	// 加载servlet
	@SuppressWarnings("rawtypes")
	public Servlet loadServlet() throws ServletException{
		// 判断servlet实例是否已经存在
		if(instance != null){
			return instance;
		}
		
		// 生命一个servlet变量
		Servlet servlet = null;
		// 在容器初始化的时候给出这个处理的servlet名字，用于初始化servlet使用。
		String actualClass = servletClass;
		if(actualClass == null)
			throw new ServletException("servlet class has not been specified");
		
		// 获取加载器
		Loader loader = getLoader();
		if(loader == null)
			throw new ServletException("No loader.");
		
		// 获取类加载器
		ClassLoader classLoader = loader.getClassLoader();
		Class classClass = null;
		try {
			if(classLoader != null)
				// 类加载器不为空的时候加载类的class字节码文件到内存中，生成servlet的Class文件对象，用于创建servlet实例使用。
				classClass = classLoader.loadClass(actualClass);
		} catch (ClassNotFoundException e) {
			throw new ServletException("Servlet class not found");
		}
		
		// 创建servlet实例
		try {
			servlet = (Servlet) classClass.newInstance();
		} catch (Exception e) {
			throw new ServletException("Failed to instantiate servlet");
		}
		
		// servlet的初始化。
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
		return servletClass;
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

	// 成员变量的修改需要加上同步操作，servlet请求的是多线程的。
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

}
