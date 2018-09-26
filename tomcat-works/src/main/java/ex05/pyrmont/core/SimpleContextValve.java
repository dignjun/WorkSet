package ex05.pyrmont.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.HttpRequest;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;
import org.apache.catalina.Wrapper;

public class SimpleContextValve implements Valve, Contained{
	
	protected Container container;
	
	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public String getInfo() {
		return null;
	}

	@SuppressWarnings("unused")
	public void invoke(Request request, Response response, ValveContext valveContext)
			throws IOException, ServletException {
		// validate the request and response object types
		if(!(request.getRequest() instanceof HttpServletRequest) ||
				!(response.getResponse() instanceof HttpServletResponse)) {
			return ;//note - Not much else we can do generically
		}
		
		// Disallow any direct access to resources under WEB-INF or META-INF
		// 这里就看到了服务器为什么不能直接访问项目中的WEB-INF or META-INF目录了，它们是受保护的。
		// 额，好像并没有不能访问的逻辑啊。。。
		HttpServletRequest hreq = (HttpServletRequest) request.getRequest();
		String contextPath = hreq.getContextPath();
		String requestURI = ((HttpRequest)request).getDecodedRequestURI();
		String relativeURI = requestURI.substring(contextPath.length()).toUpperCase();
		Context context = (Context) getContainer();
		// select the wrapper to be used for this request
		Wrapper wrapper = null;
		try {
			wrapper = (Wrapper) context.map(request, true);
		} catch (IllegalArgumentException e) {
			badRequest(requestURI, (HttpServletResponse) response.getResponse());
			return ;
		}
		if(wrapper == null){
			notFound(requestURI, (HttpServletResponse) response.getResponse());
			return ;
		}
		// ask this wrapper to process this request
		response.setContext(context);
		wrapper.invoke(request, response);
	}
	
	private void badRequest(String requestURI, HttpServletResponse response){
		try {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, requestURI);
		} catch (Exception e) {
			;
		}
	}
	private void notFound(String requestURI, HttpServletResponse response){
		try {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, requestURI);
		} catch (Exception e) {
			;
		}
	}

}





























