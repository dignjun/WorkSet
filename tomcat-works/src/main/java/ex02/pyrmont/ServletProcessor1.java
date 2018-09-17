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
 * 用于处理servlet的http请求
 * 
 * @author DINGJUN
 *
 */
public class ServletProcessor1 {

	@SuppressWarnings("rawtypes")
	public void process(Request request, Response response) {
		// 通过request调用getRequestUri方法获得URI
		// String uri = request.getUri();
		// URI的形式：/servlet/servletName
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		System.out.println("servletName:" + servletName);
		URLClassLoader loader = null;
		try {
			// 创建一个 URLClassLoader
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
//			File classPath = new File(Constants.WEB_ROOT);
			File classPath = new File("target\\classes\\ex02\\pyrmont");
			System.out.println("classpath.getCanonicalPath()：" + classPath.getCanonicalPath());
			// the forming of repository is taken from the
			// createClassLoader method in
			// org.apache.catalina.startup.ClassLoaderFactory
			// 在一个servlet容器里面，一个类加载器可以找到servlet的地方被称为资源库。
			String repository = (new URL("file", null,
					classPath.getCanonicalPath() + File.separator)).toString();
			
			// the code for forming the URL is taken from
			// the addRepository method in
			// org.apache.catalina.loader.StandardClassLoader.
			// 这里 urls 是一个 java.net.URL 的对象数组，这些对象指向了加载类时候查找的位置。任何以/结尾的 URL 都假设是一个目录。否则，URL 会 Otherwise, the URL 假定是一个将被下载并在需要的时候打开的 JAR 文件。
			// F:\github\tomcat-works\webroot\
			urls[0] = new URL(null, repository, streamHandler);
			
			// 加载servlet，可以使用URLClassLoader类。
			loader = new URLClassLoader(urls);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		Class myClass = null;
		try {
			// 通过类类加载器获得目标servlet类
			// F:\github\tomcat-works\webroot\PrimitiveServlet 但是maven工程的编译路径在target中，这里会报空指针异常。
			// F:\github\tomcat-works\webroot\ex02\pyrmont\PrimitiveServlet
//			myClass = loader.loadClass(servletName);
			myClass = loader.loadClass("ex02.pyrmont.PrimitiveServlet");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		Servlet servlet = null;
		try {
			servlet = (Servlet) myClass.newInstance();
			servlet.service((ServletRequest) request,
					(ServletResponse) response);
		} catch (Exception e) {
			System.out.println(e.toString());
		} catch (Throwable e) {
			System.out.println(e.toString());
		}
	}
}
