package ex02.pyrmont;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
  	1   当第一次调用 servlet 的时候，加载该 servlet 类并调用 servlet 的 init 方法(仅仅一次)。
  2 对每次请求，构造一个 javax.servlet.ServletRequest 实例和一个javax.servlet.ServletResponse 实例。
  3 调用 servlet 的 service 方法，同时传递 ServletRequest 和 ServletResponse 对象。
  4 当 servlet 类被关闭的时候，调用 servlet 的 destroy 方法并卸载 servlet 类。
*/
// 这是个用来测试建立起来servlet容器的，目前支持是对简单的servlet支持
public class PrimitiveServlet implements Servlet{

	// 在servlet
	// 类已经初始化之后，init 方法将会被 servlet 容器所调用。servlet 容器只调用一次，以此表明
	// servlet 已经被加载进服务中。
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init");
	}

	public ServletConfig getServletConfig() {
		return null;
	}

	// servlet 容 器 为 servlet 请求 调用它 的 service 方法 。servlet 容器 传递一 个
	// javax.servlet.ServletRequest对象和javax.servlet.ServletResponse对象。
	// 在servlet生命周期中，service方法将会给调用多次。
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		System.out.println("from service");
		PrintWriter out = response.getWriter();
		out.println("Hello. Roses are red.");
		out.print("Violets are blue.");
	}

	public String getServletInfo() {
		return null;
	}

	// 当从服务中移除一个 servlet 实例的时候，servlet 容器调用 destroy 方法。这通常发生在
	// servlet 容器正在被关闭或者 servlet 容器需要一些空闲内存的时候。
	public void destroy() {
	}

}
