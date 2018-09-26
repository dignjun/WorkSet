package ex05.pyrmont.core;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;

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
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
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

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SimpleContext implements Context, Pipeline {

	protected HashMap children = new HashMap();
	protected Loader loader = null;
	protected SimplePipeline pipeline = new SimplePipeline(this);
	protected HashMap servletMappings = new HashMap();
	protected Mapper mapper = null;
	protected HashMap mappers = new HashMap();
	private Container parent = null;

	public SimpleContext() {
		pipeline.setBasic(new SimpleContextValve());
	}

	public void addChild(Container child) {
		child.setParent(this);
		children.put(child.getName(), child);
	}

	public void addContainerListener(ContainerListener arg0) {
		// TODO Auto-generated method stub

	}

	public void addMapper(Mapper mapper) {
		// this method is adopted from addMapper in ContainerBase
		// the first mapper added becomes the default mapper
		mapper.setContainer(this);
		this.mapper = mapper;
		synchronized (mappers) {
			if (mappers.get(mapper.getProtocol()) != null)
				throw new IllegalArgumentException("addMapper:  Protocol '"
						+ mapper.getProtocol() + "' is not unique");
			mapper.setContainer(this);
			mappers.put(mapper.getProtocol(), mapper);
			if (mappers.size() == 1)
				this.mapper = mapper;
			else
				this.mapper = null;
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub

	}

	public Container findChild(String name) {
		if (name == null)
			return null;
		synchronized (children) {
			return (Container) children.get(name);
		}
	}

	public Container[] findChildren() {
		synchronized (children) {
			Container results[] = new Container[children.size()];
			return (Container[]) children.values().toArray(results);
		}
	}

	public ContainerListener[] findContainerListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public Mapper findMapper(String protocol) {
		// the default mapper will always be returned, if any,
		// regardless the value of protocol
		if (mapper != null)
			return (mapper);
		else
			synchronized (mappers) {
				return ((Mapper) mappers.get(protocol));
			}
	}

	public Mapper[] findMappers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cluster getCluster() {
		// TODO Auto-generated method stub
		return null;
	}

	/* methods of the container interface */
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Loader getLoader() {
		if (loader != null) {
			return loader;
		}
		if (parent != null) {
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

	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		pipeline.invoke(request, response);
	}

	public Container map(Request request, boolean update) {
		// this method is taken from the map method in
		// org.apache.cataline.core.ContainerBase
		// the findMapper method always returns the default mapper, if any,
		// regardless the
		// request's protocol
		Mapper mapper = findMapper(request.getRequest().getProtocol());
		if (mapper == null)
			return (null);

		// Use this Mapper to perform this mapping
		return (mapper.map(request, update));
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

	public void addApplicationListener(String arg0) {
		// TODO Auto-generated method stub

	}

	public void addApplicationParameter(ApplicationParameter arg0) {
		// TODO Auto-generated method stub

	}

	public void addConstraint(SecurityConstraint arg0) {
		// TODO Auto-generated method stub

	}

	public void addEjb(ContextEjb arg0) {
		// TODO Auto-generated method stub

	}

	public void addEnvironment(ContextEnvironment arg0) {
		// TODO Auto-generated method stub

	}

	public void addErrorPage(ErrorPage arg0) {
		// TODO Auto-generated method stub

	}

	public void addFilterDef(FilterDef arg0) {
		// TODO Auto-generated method stub

	}

	public void addFilterMap(FilterMap arg0) {
		// TODO Auto-generated method stub

	}

	public void addInstanceListener(String arg0) {
		// TODO Auto-generated method stub

	}

	public void addLocalEjb(ContextLocalEjb arg0) {
		// TODO Auto-generated method stub

	}

	public void addMimeMapping(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void addParameter(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void addResource(ContextResource arg0) {
		// TODO Auto-generated method stub

	}

	public void addResourceEnvRef(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void addResourceLink(ContextResourceLink arg0) {
		// TODO Auto-generated method stub

	}

	public void addRoleMapping(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void addSecurityRole(String arg0) {
		// TODO Auto-generated method stub

	}

	public void addServletMapping(String pattern, String name) {
		synchronized (servletMappings) {
			servletMappings.put(pattern, name);
		}
	}

	public void addTaglib(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void addWelcomeFile(String arg0) {
		// TODO Auto-generated method stub

	}

	public void addWrapperLifecycle(String arg0) {
		// TODO Auto-generated method stub

	}

	public void addWrapperListener(String arg0) {
		// TODO Auto-generated method stub

	}

	public Wrapper createWrapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findApplicationListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public ApplicationParameter[] findApplicationParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public SecurityConstraint[] findConstraints() {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextEjb findEjb(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextEjb[] findEjbs() {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextEnvironment findEnvironment(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextEnvironment[] findEnvironments() {
		// TODO Auto-generated method stub
		return null;
	}

	public ErrorPage findErrorPage(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ErrorPage findErrorPage(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ErrorPage[] findErrorPages() {
		// TODO Auto-generated method stub
		return null;
	}

	public FilterDef findFilterDef(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public FilterDef[] findFilterDefs() {
		// TODO Auto-generated method stub
		return null;
	}

	public FilterMap[] findFilterMaps() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findInstanceListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextLocalEjb findLocalEjb(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextLocalEjb[] findLocalEjbs() {
		// TODO Auto-generated method stub
		return null;
	}

	public String findMimeMapping(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findMimeMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	public String findParameter(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextResource findResource(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String findResourceEnvRef(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findResourceEnvRefs() {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextResourceLink findResourceLink(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextResourceLink[] findResourceLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	public ContextResource[] findResources() {
		// TODO Auto-generated method stub
		return null;
	}

	public String findRoleMapping(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean findSecurityRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public String[] findSecurityRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	public String findServletMapping(String pattern) {
		synchronized (servletMappings) {
			return (String) servletMappings.get(pattern);
		}
	}

	public String[] findServletMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	public String findStatusPage(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] findStatusPages() {
		// TODO Auto-generated method stub
		return null;
	}

	public String findTaglib(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findTaglibs() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean findWelcomeFile(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public String[] findWelcomeFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findWrapperLifecycles() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] findWrapperListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getApplicationListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	public CharsetMapper getCharsetMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getConfigured() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getCookies() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getCrossContext() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getDistributable() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDocBase() {
		// TODO Auto-generated method stub
		return null;
	}

	public LoginConfig getLoginConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public NamingResources getNamingResources() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getOverride() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getPrivileged() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getPublicId() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getReloadable() {
		// TODO Auto-generated method stub
		return false;
	}

	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSessionTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getWrapperClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public void reload() {
		// TODO Auto-generated method stub

	}

	public void removeApplicationListener(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeApplicationParameter(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeConstraint(SecurityConstraint arg0) {
		// TODO Auto-generated method stub

	}

	public void removeEjb(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeEnvironment(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeErrorPage(ErrorPage arg0) {
		// TODO Auto-generated method stub

	}

	public void removeFilterDef(FilterDef arg0) {
		// TODO Auto-generated method stub

	}

	public void removeFilterMap(FilterMap arg0) {
		// TODO Auto-generated method stub

	}

	public void removeInstanceListener(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeLocalEjb(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeMimeMapping(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeParameter(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeResource(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeResourceEnvRef(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeResourceLink(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeRoleMapping(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeSecurityRole(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeServletMapping(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeTaglib(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeWelcomeFile(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeWrapperLifecycle(String arg0) {
		// TODO Auto-generated method stub

	}

	public void removeWrapperListener(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setApplicationListeners(Object[] arg0) {
		// TODO Auto-generated method stub

	}

	public void setAvailable(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void setCharsetMapper(CharsetMapper arg0) {
		// TODO Auto-generated method stub

	}

	public void setConfigured(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void setCookies(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void setCrossContext(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void setDisplayName(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setDistributable(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void setDocBase(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setLoginConfig(LoginConfig arg0) {
		// TODO Auto-generated method stub

	}

	public void setNamingResources(NamingResources arg0) {
		// TODO Auto-generated method stub

	}

	public void setOverride(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void setPath(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setPrivileged(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void setPublicId(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setReloadable(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void setSessionTimeout(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setWrapperClass(String arg0) {
		// TODO Auto-generated method stub

	}

}
