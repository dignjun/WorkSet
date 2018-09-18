package ex03.pyrmont.connector.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * request的门面类
 * @author DINGJUN
 *
 */
@SuppressWarnings("rawtypes")
public class HttpRequestFacade implements HttpServletRequest{
	
	// 持有接口对象
	private HttpServletRequest request;
	
	public HttpRequestFacade(HttpServletRequest httpServletRequest){
		this.request = httpServletRequest;
	}
	
	public Object getAttribute(String name) {
		return request.getAttribute(name);
	}

	public Enumeration getAttributeNames() {
		return request.getAttributeNames();
	}

	public String getCharacterEncoding() {
		return request.getCharacterEncoding();
	}

	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {
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

	public Enumeration getParameterNames() {
		return request.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		return request.getParameterValues(name);
	}

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
		return null;
	}
	public int getLocalPort() {
		return 0;
	}
	public String getAuthType() {
		return null;
	}

	public Cookie[] getCookies() {
		return null;
	}

	public long getDateHeader(String name) {
		return 0;
	}
	public String getHeader(String name) {
		return null;
	}

	public Enumeration getHeaders(String name) {
		return null;
	}
	public Enumeration getHeaderNames() {
		return null;
	}

	public int getIntHeader(String name) {
		return 0;
	}

	public String getMethod() {
		return null;
	}

	public String getPathInfo() {
		return null;
	}

	public String getPathTranslated() {
		return null;
	}

	public String getContextPath() {
		return null;
	}
	public String getQueryString() {
		return null;
	}

	public String getRemoteUser() {
		return null;
	}

	public boolean isUserInRole(String role) {
		return false;
	}

	public Principal getUserPrincipal() {
		return null;
	}
	public String getRequestedSessionId() {
		return null;
	}
	public String getRequestURI() {
		return null;
	}

	public StringBuffer getRequestURL() {
		return null;
	}

	public String getServletPath() {
		return null;
	}

	public HttpSession getSession(boolean create) {
		return null;
	}

	public HttpSession getSession() {
		return null;
	}

	public boolean isRequestedSessionIdValid() {
		return false;
	}

	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}
	public boolean isRequestedSessionIdFromURL() {
		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}

}
