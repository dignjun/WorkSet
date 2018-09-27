package ex06.pyrmont.core;

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
 * 上下文的阀门，基础的任务
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
		// TODO Auto-generated method stub
		return null;
	}

	public void invoke(Request request, Response response,
			ValveContext valveContext) throws IOException, ServletException {
		// Validate the request and response object types
		if (!(request.getRequest() instanceof HttpServletRequest)
				|| !(response.getResponse() instanceof HttpServletResponse)) {
			return; // Not much else we can do generically
		}

		// Disallow any direct access to resources under WEB-INF or META-INF
		// 不允许直接访问WEB-INF or META-INF这两个文件夹下的文件
		HttpServletRequest hreq = (HttpServletRequest) request.getRequest();
		// 服务的项目路径
		String contextPath = hreq.getContextPath();
		// 请求的URI
		String requestURI = ((HttpRequest) request).getDecodedRequestURI();
		@SuppressWarnings("unused")
		String relaiveURI = requestURI.substring(contextPath.length())
				.toUpperCase();
		// 实例变量是顶级接口Container，这里要进行向下转型为Context容器实现。
		Context context = (Context) getContainer();
		// select the wrapper to be used for this request
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
		// Ask this Wrapper to process this Request
		response.setContext(context);
		wrapper.invoke(request, response);
	}

	// 500
	private void badRequest(String requestURI, HttpServletResponse response) {
		try {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, requestURI);
		} catch (IllegalStateException e) {
			;
		} catch (IOException e) {
			;
		}
	}

	// 404
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
