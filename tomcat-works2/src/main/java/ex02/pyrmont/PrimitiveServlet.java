package ex02.pyrmont;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PrimitiveServlet implements Servlet{

	public void init(ServletConfig config) throws ServletException {
		System.out.println("init");
	}

	public ServletConfig getServletConfig() {
		return null;
	}

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		System.out.println("from service");
		PrintWriter out = res.getWriter();
		out.println("Hello. Rose are red");
		out.print("Violets are blue");
	}

	public String getServletInfo() {
		return null;
	}

	public void destroy() {
		System.out.println("destory");
	}

}
