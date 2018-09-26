package ex05.pyrmont.container;

import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.naming.directory.DirContext;
import javax.servlet.ServletException;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Context;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Engine;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Mapper;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Service;

/**
 * tomcat容器接口Container的子接口Engine的实现之一
 * 总共有四种实现：Engine，Host，Context，Wrapper
 * 最高层次的子容器，表示整个Catalina的Servlet引擎
 * 
 * @author DINGJUN
 *
 */
public class EngineDemo implements Engine{

	public void addChild(Container arg0) {
		
	}

	public void addContainerListener(ContainerListener arg0) {
		
	}

	public void addMapper(Mapper arg0) {
		
	}

	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		
	}

	public Container findChild(String arg0) {
		return null;
	}

	public Container[] findChildren() {
		return null;
	}

	public ContainerListener[] findContainerListeners() {
		return null;
	}

	public Mapper findMapper(String arg0) {
		return null;
	}

	public Mapper[] findMappers() {
		return null;
	}

	public Cluster getCluster() {
		return null;
	}

	public String getInfo() {
		return null;
	}

	public Loader getLoader() {
		return null;
	}

	public Logger getLogger() {
		return null;
	}

	public Manager getManager() {
		return null;
	}

	public String getName() {
		return null;
	}

	public Container getParent() {
		return null;
	}

	public ClassLoader getParentClassLoader() {
		return null;
	}

	public Realm getRealm() {
		return null;
	}

	public DirContext getResources() {
		return null;
	}

	public void invoke(Request arg0, Response arg1) throws IOException,
			ServletException {
		
	}

	public Container map(Request arg0, boolean arg1) {
		return null;
	}

	public void removeChild(Container arg0) {
		
	}

	public void removeContainerListener(ContainerListener arg0) {
		
	}

	public void removeMapper(Mapper arg0) {
		
	}

	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		
	}

	public void setCluster(Cluster arg0) {
		
	}

	public void setLoader(Loader arg0) {
		
	}

	public void setLogger(Logger arg0) {
		
	}

	public void setManager(Manager arg0) {
		
	}

	public void setName(String arg0) {
		
	}

	public void setParent(Container arg0) {
		
	}

	public void setParentClassLoader(ClassLoader arg0) {
		
	}

	public void setRealm(Realm arg0) {
		
	}

	public void setResources(DirContext arg0) {
		
	}

	public void addDefaultContext(DefaultContext arg0) {
		
	}

	public DefaultContext getDefaultContext() {
		return null;
	}

	public String getDefaultHost() {
		return null;
	}

	public String getJvmRoute() {
		return null;
	}

	public Service getService() {
		return null;
	}

	public void importDefaultContext(Context arg0) {
		
	}

	public void setDefaultHost(String arg0) {
		
	}

	public void setJvmRoute(String arg0) {
		
	}

	public void setService(Service arg0) {
		
	}

}
