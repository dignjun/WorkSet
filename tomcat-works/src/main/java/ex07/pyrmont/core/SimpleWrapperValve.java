package ex07.pyrmont.core;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

/**
 * 基础的阀门任务
 * 
 * @author DINGJUN
 *
 */
public class SimpleWrapperValve implements Valve, Contained{

	protected Container container;
	
	public String getInfo() {
		return null;
	}

	// 这是一个基础的阀门任务，对应一个servlet。。
	public void invoke(Request request, Response response, ValveContext valveContext)
			throws IOException, ServletException {
		// 连接器将请求交给容器，父容器将请求交给子容器，子容器将请求交给流水线，流水线将请求委托给阀门，子容器获取到servlet，执行这个请求
		SimpleWrapper wrapper = (SimpleWrapper) getContainer();
		ServletRequest sreq = request.getRequest();
		ServletResponse sres = response.getResponse();
		Servlet servlet = null;
		HttpServletRequest hreq = null;
		if(sreq instanceof HttpServletRequest)
			hreq = (HttpServletRequest) sreq;
		HttpServletResponse hres = null;
		if(sres instanceof HttpServletResponse)
			hres = (HttpServletResponse) sres;
		
		try {
			// 分配一个servlet处理请求
			servlet = wrapper.allocate();
			if(hres != null && hreq != null){
				servlet.service(hreq, hres);
			}else{
				servlet.service(sreq, sres);
			}
		} catch (Exception e) {
		}
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

}


























