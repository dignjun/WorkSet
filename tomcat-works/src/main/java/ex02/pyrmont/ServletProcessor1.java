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
			// TODO // 使用web_root实现不了，只好将路径直接修改为类的路径了QAQ
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
			// F:\github\tomcat-works\webroot\PrimitiveServlet 实际拼接的路径，但是maven工程的编译路径在target中，这里会报空指针异常。
			// F:\github\tomcat-works\webroot\ex02\pyrmont\PrimitiveServlet 如果编译输出路径是webroot的话，也应该是这样的路径。
			//			myClass = loader.loadClass(servletName);
			// TODO 这里好像并不能像上面的写法直接使用serlvet的类名，然后拼接web_root的路径得到class文件的绝对路径并且加载这个类，所以这里的为了测试直接将类的限定名copy了过来。
			myClass = loader.loadClass("ex02.pyrmont.PrimitiveServlet");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		Servlet servlet = null;
		try {
			servlet = (Servlet) myClass.newInstance();
			
			// TODO 这里的转型是看不懂的，明明是实现类，形参传递一点问题都没有，这里为什么要特地的向下转型呢？ 为此，改进出了2
			// 拥有一个Request实例，它们就可以调用parse方法。拥有一个 Response 实例，就可以调用 sendStaticResource 方法
			// 所以这里的做了转型操作，转型之后的request和response方法就是父类型了，就会忽略实现的特有的特征了，servlet的serive中就不能使用上面提到的两个方法了。
			servlet.service((ServletRequest) request,
					(ServletResponse) response);
		} catch (Exception e) {
			System.out.println(e.toString());
		} catch (Throwable e) {
			System.out.println(e.toString());
		}
	}
}




































