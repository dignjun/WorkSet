package ex07.pyrmont.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Container;
import org.apache.catalina.HttpRequest;
import org.apache.catalina.Mapper;
import org.apache.catalina.Request;
import org.apache.catalina.Wrapper;

/**
 * 
 * Mapper???
 * 
 * 
 * @author DINGJUN
 *
 */
public class SimpleContextMapper implements Mapper {

	// 注意：这里的这个类可是很具体的，并没有像其他的实现中，笼统的这里给个接口Container
	private SimpleContext context = null;

	public Container getContainer() {
		return context;
	}

	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	// 父容器会调用这个方法来将请求委托给子容器。
	public Container map(Request request, boolean update) {
		if (update && (request.getWrapper() != null))
			return request.getWrapper();
		String contextPath = ((HttpServletRequest) request.getRequest())
				.getContextPath();
		String requestURI = ((HttpRequest) request).getDecodedRequestURI();
		String relativeURI = requestURI.substring(contextPath.length());
		Wrapper wrapper = null;
		String servletPath = requestURI;
		String pathInfo = null;
		String name = context.findServletMapping(relativeURI);
		if (name != null)
			wrapper = (Wrapper) context.findChild(name);

		if (update) {
			request.setWrapper(wrapper);
			((HttpRequest) request).setServletPath(servletPath);
			((HttpRequest) request).setPathInfo(pathInfo);
		}
		return (wrapper);
	}

	public void setContainer(Container container) {
		if ((container instanceof SimpleContext))
			throw new IllegalArgumentException("Illega type of container");
		context = (SimpleContext) container;
	}

	public void setProtocol(String arg0) {
		// TODO Auto-generated method stub

	}

}
