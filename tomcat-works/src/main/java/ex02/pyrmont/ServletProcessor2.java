package ex02.pyrmont;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 修改后的servlet调用的servlet处理类
 * 
 * @author DINGJUN
 *
 */
public class ServletProcessor2 {

	@SuppressWarnings("rawtypes")
	public void process(Request request, Response response) {
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		System.out.println("servletName:" + servletName);
		URLClassLoader loader = null;
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File("target\\classes\\ex02\\pyrmont");
			System.out.println("classpath.getCanonicalPath()：" + classPath.getCanonicalPath());
			String repository = (new URL("file", null,
					classPath.getCanonicalPath() + File.separator)).toString();
			
			urls[0] = new URL(null, repository, streamHandler);
			loader = new URLClassLoader(urls);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		Class myClass = null;
		try {
			myClass = loader.loadClass("ex02.pyrmont.PrimitiveServlet");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		Servlet servlet = null;
		/*修改的地方*/
		RequestFacade requestFacade = new RequestFacade(request);
		ResponseFacade responseFacade = new ResponseFacade(response);
		try {
			servlet = (Servlet) myClass.newInstance();
			servlet.service((ServletRequest) requestFacade,
					(ServletResponse) responseFacade);
		} catch (Exception e) {
			System.out.println(e.toString());
		} catch (Throwable e) {
			System.out.println(e.toString());
		}
	}
}
