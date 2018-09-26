package ex05.pyrmont.core;

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

/**
 * 包装器
 * 一个包装器servlet表示一个独立servlet定义的容器。包装器继承了Container接口，并且添加了几个方法。
 * 包装器的实现类负责管理其下层servlet的生命周期，包括servlet的init，service，和destory方法。
 * allocate 方法负责定位该包装器表示的 servlet 的实例，另一个明显是加载servlet的方法。
 * 
 * @author DINGJUN
 *
 */
public class SimpleWrapper implements Wrapper, Pipeline{

	// servlet实例
	private Servlet instance = null;
	// sevlet的限定名
	private String servletClass;
	// 类加载器
	private Loader loader;
	// servlet的名称
	private String name;
	// 这个容器所拥有的流水线。同时这个容器关联对应的流水线。
	private SimplePipeline pipeline = new SimplePipeline(this);
	protected Container parent = null;
	
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
		return null;
	}

	// 获取加载器
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

	// 容器的invoke方法调用的其实就是流水线的invoke方法。而流水线的invoke方法会将处理请求
	// 委托给他的内部类来处理
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

	public void addInitParameter(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addInstanceListener(InstanceListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addSecurityReference(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	// 
	public Servlet allocate() throws ServletException {
		// Load and initialize our instance if necessary
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
	
	// 加载web应用的servlet
	@SuppressWarnings("rawtypes")
	private Servlet loadServlet() throws ServletException {
		if(instance!=null){
			return instance;
		}
		Servlet servlet = null;
		String actualClass = servletClass;
		if(actualClass == null){
			throw new ServletException("servlet class has not been specified");
		}
		
		Loader loader = getLoader();
		// Acquire an instance of the class loader to be used
		if(loader == null){
			throw new ServletException("No loader.");
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
		
		// Instantiate and initialize an instance of the servlet class itself
		try {
			servlet = (Servlet) classClass.newInstance();
		} catch (Exception e) {
			throw new ServletException("Failed to instantiate servlet");
		}
		// Call the initialization method of this servlet
		try {
			servlet.init(null);
		} catch (Throwable e) {
			throw new ServletException("Failed initialize servlet");
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
		instance = loadServlet();
	}

	public void removeInitParameter(String arg0) {
		
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

	// 添加一个valve阀门
	public void addValve(Valve valve) {
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
