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
	@Override
	public Object getAttribute(String name) {
		return null;
	}
	@Override
	public Enumeration getAttributeNames() {
		return null;
	}
	@Override
	public String getCharacterEncoding() {
		return null;
	}
	@Override
	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {
	}
	@Override
	public int getContentLength() {
		return 0;
	}
	@Override
	public String getContentType() {
		return null;
	}
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return null;
	}
	@Override
	public String getParameter(String name) {
		return null;
	}
	@Override
	public Enumeration getParameterNames() {
		return null;
	}
	@Override
	public String[] getParameterValues(String name) {
		return null;
	}
	@Override
	public Map getParameterMap() {
		return null;
	}
	@Override
	public String getProtocol() {
		return null;
	}
	@Override
	public String getScheme() {
		return null;
	}
	@Override
	public String getServerName() {
		return null;
	}
	@Override
	public int getServerPort() {
		return 0;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return null;
	}

	@Override
	public String getRemoteAddr() {
		return null;
	}

	@Override
	public String getRemoteHost() {
		return null;
	}

	@Override
	public void setAttribute(String name, Object o) {
	}

	@Override
	public void removeAttribute(String name) {
	}

	@Override
	public Locale getLocale() {
		return null;
	}

	@Override
	public Enumeration getLocales() {
		return null;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}

	@Override
	public String getRealPath(String path) {
		return null;
	}

	@Override
	public int getRemotePort() {
		return 0;
	}

	@Override
	public String getLocalName() {
		return null;
	}

	@Override
	public String getLocalAddr() {
		return null;
	}

	@Override
	public int getLocalPort() {
		return 0;
	}

	@Override
	public String getAuthType() {
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		return null;
	}

	@Override
	public long getDateHeader(String name) {
		return 0;
	}

	@Override
	public String getHeader(String name) {
		return null;
	}

	@Override
	public Enumeration getHeaders(String name) {
		return null;
	}

	@Override
	public Enumeration getHeaderNames() {
		return null;
	}

	@Override
	public int getIntHeader(String name) {
		return 0;
	}

	@Override
	public String getMethod() {
		return null;
	}

	@Override
	public String getPathInfo() {
		return null;
	}

	@Override
	public String getPathTranslated() {
		return null;
	}

	@Override
	public String getContextPath() {
		return null;
	}

	@Override
	public String getQueryString() {
		return null;
	}

	@Override
	public String getRemoteUser() {
		return null;
	}

	@Override
	public boolean isUserInRole(String role) {
		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		return null;
	}

	@Override
	public String getRequestURI() {
		return null;
	}

	@Override
	public StringBuffer getRequestURL() {
		return null;
	}

	@Override
	public String getServletPath() {
		return null;
	}

	@Override
	public HttpSession getSession(boolean create) {
		return null;
	}

	@Override
	public HttpSession getSession() {
		return null;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}
}
