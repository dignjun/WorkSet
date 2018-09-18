package ex03.pyrmont.connector.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ex03.pyrmont.connector.util.ParameterMap;

/**
 * HttpRequest实现HttpServletRequest接口，
 * 
 * @author DINGJUN
 *
 */
@SuppressWarnings("rawtypes")
public class HttpRequest implements HttpServletRequest{

	private String uri;
	private InputStream inputStream;
	
	// http请求的头部，cookies和参数。
	protected String queryString;
	protected String requestedSessionId;
	protected Boolean requestedSessionURL;
	protected String requestURI;
	protected String method;
	protected String protocol;
	protected HashMap headers = new HashMap();
	protected ArrayList cookies = new ArrayList();
	protected ParameterMap parameters = null;
	
	// 构造给出socket的输入流。
	public HttpRequest(InputStream inputStream){
		this.inputStream = inputStream;
	}
	// 获取uri
	public String getUri(){
		return uri;
	}
	// 解析uri
	private String parseUri(String requestString){
		int index1,index2;
		index1 = requestString.indexOf(' ');
		if(index1 != -1){
			index2 = requestString.indexOf(' ', index1 + 1);
			if(index2 > index1){
				return requestString.substring(index1 + 1, index2);
			}
		}
		return null;
	}
	// 解析socket
	public void parse(){
		// 从socket中读取数据
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = inputStream.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		for(int j=0; j<i; j++){
			request.append((char)buffer[j]);
		}
		System.out.println(request.toString());
		uri = parseUri(request.toString());
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	public Boolean getRequestedSessionURL() {
		return requestedSessionURL;
	}
	public void setRequestedSessionURL(Boolean requestedSessionURL) {
		this.requestedSessionURL = requestedSessionURL;
	}
	public void setRequestedSessionId(String requestedSessionId) {
		this.requestedSessionId = requestedSessionId;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	/*--------实现的方法--------*/
	public Object getAttribute(String name) {
		return null;
	}
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
	public Enumeration getParameterNames() {
		return null;
	}
	public String[] getParameterValues(String name) {
		return null;
	}
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
		return requestURI;
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
