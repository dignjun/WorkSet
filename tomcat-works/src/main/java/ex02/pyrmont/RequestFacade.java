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
	@Override
	public Object getAttribute(String name) {
		return request.getAttribute(name);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getAttributeNames() {
		return request.getAttributeNames();
	}
	@Override
	public String getCharacterEncoding() {
		return request.getCharacterEncoding();
	}
	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
	}
	@Override
	public int getContentLength() {
		return request.getContentLength();
	}
	@Override
	public String getContentType() {
		return request.getContentType();
	}
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return request.getInputStream();
	}
	@Override
	public String getParameter(String name) {
		return request.getParameter(name);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getParameterNames() {
		return request.getParameterNames();
	}
	@Override
	public String[] getParameterValues(String name) {
		return request.getParameterValues(name);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Map getParameterMap() {
		return request.getParameterMap();
	}
	@Override
	public String getProtocol() {
		return request.getProtocol();
	}
	@Override
	public String getScheme() {
		return request.getScheme();
	}
	@Override
	public String getServerName() {
		return request.getServerName();
	}
	@Override
	public int getServerPort() {
		return request.getServerPort();
	}
	@Override
	public BufferedReader getReader() throws IOException {
		return request.getReader();
	}
	@Override
	public String getRemoteAddr() {
		return request.getRemoteAddr();
	}
	@Override
	public String getRemoteHost() {
		return request.getRemoteHost();
	}
	@Override
	public void setAttribute(String name, Object o) {
	}
	@Override
	public void removeAttribute(String name) {
	}
	@Override
	public Locale getLocale() {
		return request.getLocale();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getLocales() {
		return request.getLocales();
	}
	@Override
	public boolean isSecure() {
		return request.isSecure();
	}
	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return request.getRequestDispatcher(path);
	}
	@SuppressWarnings("deprecation")
	@Override
	public String getRealPath(String path) {
		return request.getRealPath(path);
	}
	@Override
	public int getRemotePort() {
		return request.getRemotePort();
	}
	@Override
	public String getLocalName() {
		return request.getLocalName();
	}
	@Override
	public String getLocalAddr() {
		return request.getLocalAddr();
	}
	@Override
	public int getLocalPort() {
		return request.getLocalPort();
	}
}




































