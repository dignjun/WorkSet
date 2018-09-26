package ex05.pyrmont.container;

import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Context;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Mapper;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Wrapper;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.deploy.ContextEjb;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextLocalEjb;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.deploy.ContextResourceLink;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.NamingResources;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.util.CharsetMapper;

/**
 * 表示一个Web应用，一个context包含一个或多个wrapper
 * 
 * @author DINGJUN
 *
 */
public class ContextDemo implements Context{

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

	public void addApplicationListener(String arg0) {
		
	}

	public void addApplicationParameter(ApplicationParameter arg0) {
		
	}

	public void addConstraint(SecurityConstraint arg0) {
		
	}

	public void addEjb(ContextEjb arg0) {
		
	}

	public void addEnvironment(ContextEnvironment arg0) {
		
	}

	public void addErrorPage(ErrorPage arg0) {
		
	}

	public void addFilterDef(FilterDef arg0) {
		
	}

	public void addFilterMap(FilterMap arg0) {
		
	}

	public void addInstanceListener(String arg0) {
		
	}

	public void addLocalEjb(ContextLocalEjb arg0) {
		
	}

	public void addMimeMapping(String arg0, String arg1) {
		
	}

	public void addParameter(String arg0, String arg1) {
		
	}

	public void addResource(ContextResource arg0) {
		
	}

	public void addResourceEnvRef(String arg0, String arg1) {
		
	}

	public void addResourceLink(ContextResourceLink arg0) {
		
	}

	public void addRoleMapping(String arg0, String arg1) {
		
	}

	public void addSecurityRole(String arg0) {
		
	}

	public void addServletMapping(String arg0, String arg1) {
		
	}

	public void addTaglib(String arg0, String arg1) {
		
	}

	public void addWelcomeFile(String arg0) {
		
	}

	public void addWrapperLifecycle(String arg0) {
		
	}

	public void addWrapperListener(String arg0) {
		
	}

	public Wrapper createWrapper() {
		return null;
	}

	public String[] findApplicationListeners() {
		return null;
	}

	public ApplicationParameter[] findApplicationParameters() {
		return null;
	}

	public SecurityConstraint[] findConstraints() {
		return null;
	}

	public ContextEjb findEjb(String arg0) {
		return null;
	}

	public ContextEjb[] findEjbs() {
		return null;
	}

	public ContextEnvironment findEnvironment(String arg0) {
		return null;
	}

	public ContextEnvironment[] findEnvironments() {
		return null;
	}

	public ErrorPage findErrorPage(int arg0) {
		return null;
	}

	public ErrorPage findErrorPage(String arg0) {
		return null;
	}

	public ErrorPage[] findErrorPages() {
		return null;
	}

	public FilterDef findFilterDef(String arg0) {
		return null;
	}

	public FilterDef[] findFilterDefs() {
		return null;
	}

	public FilterMap[] findFilterMaps() {
		return null;
	}

	public String[] findInstanceListeners() {
		return null;
	}

	public ContextLocalEjb findLocalEjb(String arg0) {
		return null;
	}

	public ContextLocalEjb[] findLocalEjbs() {
		return null;
	}

	public String findMimeMapping(String arg0) {
		return null;
	}

	public String[] findMimeMappings() {
		return null;
	}

	public String findParameter(String arg0) {
		return null;
	}

	public String[] findParameters() {
		return null;
	}

	public ContextResource findResource(String arg0) {
		return null;
	}

	public String findResourceEnvRef(String arg0) {
		return null;
	}

	public String[] findResourceEnvRefs() {
		return null;
	}

	public ContextResourceLink findResourceLink(String arg0) {
		return null;
	}

	public ContextResourceLink[] findResourceLinks() {
		return null;
	}

	public ContextResource[] findResources() {
		return null;
	}

	public String findRoleMapping(String arg0) {
		return null;
	}

	public boolean findSecurityRole(String arg0) {
		return false;
	}

	public String[] findSecurityRoles() {
		return null;
	}

	public String findServletMapping(String arg0) {
		return null;
	}

	public String[] findServletMappings() {
		return null;
	}

	public String findStatusPage(int arg0) {
		return null;
	}

	public int[] findStatusPages() {
		return null;
	}

	public String findTaglib(String arg0) {
		return null;
	}

	public String[] findTaglibs() {
		return null;
	}

	public boolean findWelcomeFile(String arg0) {
		return false;
	}

	public String[] findWelcomeFiles() {
		return null;
	}

	public String[] findWrapperLifecycles() {
		return null;
	}

	public String[] findWrapperListeners() {
		return null;
	}

	public Object[] getApplicationListeners() {
		return null;
	}

	public boolean getAvailable() {
		return false;
	}

	public CharsetMapper getCharsetMapper() {
		return null;
	}

	public boolean getConfigured() {
		return false;
	}

	public boolean getCookies() {
		return false;
	}

	public boolean getCrossContext() {
		return false;
	}

	public String getDisplayName() {
		return null;
	}

	public boolean getDistributable() {
		return false;
	}

	public String getDocBase() {
		return null;
	}

	public LoginConfig getLoginConfig() {
		return null;
	}

	public NamingResources getNamingResources() {
		return null;
	}

	public boolean getOverride() {
		return false;
	}

	public String getPath() {
		return null;
	}

	public boolean getPrivileged() {
		return false;
	}

	public String getPublicId() {
		return null;
	}

	public boolean getReloadable() {
		return false;
	}

	public ServletContext getServletContext() {
		return null;
	}

	public int getSessionTimeout() {
		return 0;
	}

	public String getWrapperClass() {
		return null;
	}

	public void reload() {
		
	}

	public void removeApplicationListener(String arg0) {
		
	}

	public void removeApplicationParameter(String arg0) {
		
	}

	public void removeConstraint(SecurityConstraint arg0) {
		
	}

	public void removeEjb(String arg0) {
		
	}

	public void removeEnvironment(String arg0) {
		
	}

	public void removeErrorPage(ErrorPage arg0) {
		
	}

	public void removeFilterDef(FilterDef arg0) {
		
	}

	public void removeFilterMap(FilterMap arg0) {
		
	}

	public void removeInstanceListener(String arg0) {
		
	}

	public void removeLocalEjb(String arg0) {
		
	}

	public void removeMimeMapping(String arg0) {
		
	}

	public void removeParameter(String arg0) {
		
	}

	public void removeResource(String arg0) {
		
	}

	public void removeResourceEnvRef(String arg0) {
		
	}

	public void removeResourceLink(String arg0) {
		
	}

	public void removeRoleMapping(String arg0) {
		
	}

	public void removeSecurityRole(String arg0) {
		
	}

	public void removeServletMapping(String arg0) {
		
	}

	public void removeTaglib(String arg0) {
		
	}

	public void removeWelcomeFile(String arg0) {
		
	}

	public void removeWrapperLifecycle(String arg0) {
		
	}

	public void removeWrapperListener(String arg0) {
		
	}

	public void setApplicationListeners(Object[] arg0) {
		
	}

	public void setAvailable(boolean arg0) {
		
	}

	public void setCharsetMapper(CharsetMapper arg0) {
		
	}

	public void setConfigured(boolean arg0) {
		
	}

	public void setCookies(boolean arg0) {
		
	}

	public void setCrossContext(boolean arg0) {
		
	}

	public void setDisplayName(String arg0) {
		
	}

	public void setDistributable(boolean arg0) {
		
	}

	public void setDocBase(String arg0) {
		
	}

	public void setLoginConfig(LoginConfig arg0) {
		
	}

	public void setNamingResources(NamingResources arg0) {
		
	}

	public void setOverride(boolean arg0) {
		
	}

	public void setPath(String arg0) {
		
	}

	public void setPrivileged(boolean arg0) {
		
	}

	public void setPublicId(String arg0) {
		
	}

	public void setReloadable(boolean arg0) {
		
	}

	public void setSessionTimeout(int arg0) {
		
	}

	public void setWrapperClass(String arg0) {
		
	}

}
