package ex10.pyrmont.core;

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

public class SimpleWrapper implements Wrapper, Pipeline, Lifecycle{

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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
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

	public void invoke(Request arg0, Response arg1) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		
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

	public void setLoader(Loader arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setLogger(Logger arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setManager(Manager arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setParent(Container arg0) {
		// TODO Auto-generated method stub
		
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

	public void start() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	public void stop() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	public void addValve(Valve arg0) {
		// TODO Auto-generated method stub
		
	}

	public Valve getBasic() {
		// TODO Auto-generated method stub
		return null;
	}

	public Valve[] getValves() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeValve(Valve arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setBasic(Valve arg0) {
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

	public Servlet allocate() throws ServletException {
		// TODO Auto-generated method stub
		return null;
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

	public void setServletClass(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void unavailable(UnavailableException arg0) {
		// TODO Auto-generated method stub
		
	}

	public void unload() throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
