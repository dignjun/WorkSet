package ex02.pyrmont;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
/**
 * 为了避免servlet的propercess类中的转型，这里引入servletrequest的门面类
 * RequestFacade实现了ServletRequest，同时持有ServletRequest对象的引用
 * 所有的实现通过ServletRequest对象的实现，类似于包装，同时在构造函数中传入一个实现
 * ServletRequest接口的对象，并将它持有的ServletRequest引用指向这个实现。
 * 
 * 解决了request类和response类中的特有方法会被servlet的service中使用的问题而产生的转型问题。
 * 
 * @author DINGJUN
 *
 */
public class RequestFacade implements ServletRequest{

	// 持有接口的引用，注意这里的修饰符是私有
	private ServletRequest request = null;
	// 这里就已经进行了向上转型了
	public RequestFacade(Request request) {
		this.request = request;
	}
	/*实现全部是接口的实现*/
	public Object getAttribute(String name) {
		return request.getAttribute(name);
	}
	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		return request.getAttributeNames();
	}
	public String getCharacterEncoding() {
		return request.getCharacterEncoding();
	}
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
	}
	public int getContentLength() {
		return request.getContentLength();
	}
	public String getContentType() {
		return request.getContentType();
	}
	public ServletInputStream getInputStream() throws IOException {
		return request.getInputStream();
	}
	public String getParameter(String name) {
		return request.getParameter(name);
	}
	@SuppressWarnings("rawtypes")
	public Enumeration getParameterNames() {
		return request.getParameterNames();
	}
	public String[] getParameterValues(String name) {
		return request.getParameterValues(name);
	}
	@SuppressWarnings("rawtypes")
	public Map getParameterMap() {
		return request.getParameterMap();
	}
	public String getProtocol() {
		return request.getProtocol();
	}
	public String getScheme() {
		return request.getScheme();
	}
	public String getServerName() {
		return request.getServerName();
	}
	public int getServerPort() {
		return request.getServerPort();
	}
	public BufferedReader getReader() throws IOException {
		return request.getReader();
	}
	public String getRemoteAddr() {
		return request.getRemoteAddr();
	}
	public String getRemoteHost() {
		return request.getRemoteHost();
	}
	public void setAttribute(String name, Object o) {
	}
	public void removeAttribute(String name) {
	}
	public Locale getLocale() {
		return request.getLocale();
	}
	@SuppressWarnings("rawtypes")
	public Enumeration getLocales() {
		return request.getLocales();
	}
	public boolean isSecure() {
		return request.isSecure();
	}
	public RequestDispatcher getRequestDispatcher(String path) {
		return request.getRequestDispatcher(path);
	}
	@SuppressWarnings("deprecation")
	public String getRealPath(String path) {
		return request.getRealPath(path);
	}
	public int getRemotePort() {
		return request.getRemotePort();
	}
	public String getLocalName() {
		return request.getLocalName();
	}
	public String getLocalAddr() {
		return request.getLocalAddr();
	}
	public int getLocalPort() {
		return request.getLocalPort();
	}
}




































