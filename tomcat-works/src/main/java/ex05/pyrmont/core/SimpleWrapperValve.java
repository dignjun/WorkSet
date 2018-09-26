package ex05.pyrmont.core;

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
 * 阀门接口表示一个阀门，该组件负责处理请求。这个接口有两个方法，invoke和getInfo方法。
 * SimpleWrapperValve 类是一个给 SimpleWrapper 类专门处理请求的基本阀门
 * @author DINGJUN
 *
 */
public class SimpleWrapperValve implements Valve, Contained {

	protected Container container;
	
	public void invoke(Request request, Response response, ValveContext valveContext)
			throws IOException, ServletException {
		SimpleWrapper wrapper = (SimpleWrapper) getContainer();
		ServletRequest sreq = request.getRequest();
		ServletResponse sres = response.getResponse();
		Servlet servlet = null;
		HttpServletRequest hreq = null;
		if(sreq instanceof HttpServletRequest){
			hreq = (HttpServletRequest) sreq;
		}
		HttpServletResponse hres = null;
		if(sres instanceof HttpServletResponse){
			hres = (HttpServletResponse) sres;
		}
		
		// allocate a servlet instace to precess this request
		// 加载指定的servlet处理这个请求
		try {
			servlet = wrapper.allocate();
			if(hres!=null && hreq!=null){
				servlet.service(hreq, hres);
			} else {
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
	
	public String getInfo() {
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
