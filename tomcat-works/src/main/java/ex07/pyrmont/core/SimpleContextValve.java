package ex07.pyrmont.core;

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

/**
 * 基础的阀门任务
 * 
 * @author DINGJUN
 *
 */
public class SimpleContextValve implements Valve, Contained {

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

	/**
	 * 注意：这里的invoke方法和Wrapper中的invoke方法区别很大，在Wrapper容器中的invoke就是获取Wrapper容器实现并且
	 * 通过这个实例获取到一个处理请求的serlvet，但是这里是不同的，因为这是一个父容器，所以他会将请求委托给子容器，然后进行操作。
	 * 
	 */
	@SuppressWarnings("unused")
	public void invoke(Request request, Response response,
			ValveContext valveContext) throws IOException, ServletException {
		if (!(request.getRequest() instanceof HttpServletRequest)
				|| !(response.getResponse() instanceof HttpServletResponse)) {
			return;
		}
		// 这里会有一个阻止直接访问WEB-INF和META-INF的逻辑
		// 注意：目前这个容器是Context，在子容器Wrapper中并没有这种逻辑判断
		HttpServletRequest hreq = (HttpServletRequest) request.getRequest();
		String contextPath = hreq.getContextPath();
		String requestURI = ((HttpRequest) request).getDecodedRequestURI();
		String relativeURI = requestURI.substring(contextPath.length())
				.toLowerCase();

		Context context = (Context) getContainer();
		Wrapper wrapper = null;
		try {
			wrapper = (Wrapper) context.map(request, true);
		} catch (IllegalArgumentException e) {
			badRequest(requestURI, (HttpServletResponse) response.getResponse());
			return;
		}
		if (wrapper == null) {
			notFound(requestURI, (HttpServletResponse) response.getResponse());
			return;
		}
		response.setContext(context);
		wrapper.invoke(request, response);
	}

	private void badRequest(String requestURI, HttpServletResponse response) {
		try {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, requestURI);
		} catch (IllegalStateException e) {
			;
		} catch (IOException e) {
			;
		}
	}

	private void notFound(String requestURI, HttpServletResponse response) {
		try {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, requestURI);
		} catch (IllegalStateException e) {
			;
		} catch (IOException e) {
			;
		}
	}

}
