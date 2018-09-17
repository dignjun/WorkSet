package ex02.pyrmont;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;

/**
 * 请求类：socket服务接收到请求之后创建这个类，并通过socket获取到的inputstream
 * 对发上来的请求进行解析。它会传递给servlet的service方法。
 * 
 * servlet的service方法从servlet容器中接受一个javax.servlet.ServletRequest实例和一个javax.servlet.ServletResponse 实例，
 * 也就是说对一个每一个http请求，servlet容器必须构造一个servletRequest对象和一个servletResponse对象，并把他们传递给正在服务的servlet的service方法。
 * 
 * @author DINGJUN
 *
 */
public class Request implements ServletRequest {

	private InputStream input;
	private String uri;

	// 构造初始化，传入请求的输入流
	public Request(InputStream input) {
		this.input = input;
	}

	public String getUri() {
		System.out.println("解析到的uri：" + uri);
		return uri;
	}

	// 解析请求中的uri
	// 如：http://machineName:port/staticResource这个请求的uri就是/staticResource
	private String parseUri(String requestString) {
		int index1, index2;
		index1 = requestString.indexOf(' ');
		if (index1 != -1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1)
				return requestString.substring(index1 + 1, index2);
		}
		return null;
	}

	public void parse() {
		// 从socket中读取数据集
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			request.append((char) buffer[j]);
		}
		System.out.print(request.toString());
		uri = parseUri(request.toString());
	}

	/*---------要实现的方法---------*/
	public Object getAttribute(String name) {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		return null;
	}
	public String getCharacterEncoding() {
		return null;
	}
	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {
	}
	public int getContentLength() {
		return 0;
	}
	public String getContentType() {
		return null;
	}
	public ServletInputStream getInputStream() throws IOException {
		return null;
	}
	public String getParameter(String name) {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public Enumeration getParameterNames() {
		return null;
	}
	public String[] getParameterValues(String name) {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public Map getParameterMap() {
		return null;
	}
	public String getProtocol() {
		return null;
	}
	public String getScheme() {
		return null;
	}
	public String getServerName() {
		return null;
	}
	public int getServerPort() {
		return 0;
	}
	public BufferedReader getReader() throws IOException {
		return null;
	}
	public String getRemoteAddr() {
		return null;
	}
	public String getRemoteHost() {
		return null;
	}
	public void setAttribute(String name, Object o) {
	}
	public void removeAttribute(String name) {
	}
	public Locale getLocale() {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public Enumeration getLocales() {
		return null;
	}
	public boolean isSecure() {
		return false;
	}
	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}
	public String getRealPath(String path) {
		return null;
	}
	public int getRemotePort() {
		return 0;
	}
	public String getLocalName() {
		return null;
	}
	public String getLocalAddr() {
		return null;
	}
	public int getLocalPort() {
		return 0;
	}
}
