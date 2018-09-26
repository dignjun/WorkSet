package ex05.pyrmont.container;

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
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Wrapper;

/**
 * 最低层次的子容器，表示一个独立的Servlet
 * 
 * @author DINGJUN
 *
 */
public class WrapperDemo implements Wrapper{

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

	public void addInitParameter(String arg0, String arg1) {
		
	}

	public void addInstanceListener(InstanceListener arg0) {
		
	}

	public void addSecurityReference(String arg0, String arg1) {
		
	}

	public Servlet allocate() throws ServletException {
		return null;
	}

	public void deallocate(Servlet arg0) throws ServletException {
		
	}

	public String findInitParameter(String arg0) {
		return null;
	}

	public String[] findInitParameters() {
		return null;
	}

	public String findSecurityReference(String arg0) {
		return null;
	}

	public String[] findSecurityReferences() {
		return null;
	}

	public long getAvailable() {
		return 0;
	}

	public String getJspFile() {
		return null;
	}

	public int getLoadOnStartup() {
		return 0;
	}

	public String getRunAs() {
		return null;
	}

	public String getServletClass() {
		return null;
	}

	public boolean isUnavailable() {
		return false;
	}

	public void load() throws ServletException {
		
	}

	public void removeInitParameter(String arg0) {
		
	}

	public void removeInstanceListener(InstanceListener arg0) {
		
	}

	public void removeSecurityReference(String arg0) {
		
	}

	public void setAvailable(long arg0) {
		
	}

	public void setJspFile(String arg0) {
		
	}

	public void setLoadOnStartup(int arg0) {
	}

	public void setRunAs(String arg0) {
		
	}

	public void setServletClass(String arg0) {
		
	}

	public void unavailable(UnavailableException arg0) {
		
	}

	public void unload() throws ServletException {
		
	}

}
