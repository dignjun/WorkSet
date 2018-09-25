package ex032.pyrmont.connector.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.Enumerator;
import org.apache.catalina.util.ParameterMap;
import org.apache.catalina.util.RequestUtil;

import ex032.pyrmont.connector.RequestStream;

/**
 * 请求类
 * 
 * @author DINGJUN
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class HttpRequest implements HttpServletRequest {

	private String contentType;
	private int contentLength;
	private InetAddress inetAddress;
	private InputStream input;
	private String method;
	private String protocol;
	private String queryString;
	private String requestURI;
	private String serverName;
	private int serverPort;
	private Socket socket;
	private boolean requestedSessionCookie;
	private String requestedSessionId;
	private boolean requestedSessionURL;

	// 请求参数属性
	protected HashMap attributes = new HashMap();

	// 请求的授权信息
	protected String authorization = null;

	// 请求的上下文路径
	protected String contextPath = "";

	// 请求的cookies集合
	protected ArrayList cookies = new ArrayList();

	// 空的集合用来返回空的枚举类型。不要放任何的元素到这个集合里面
	protected static ArrayList empty = new ArrayList();

	// 在getDataHeader中使用到的时间格式
	protected SimpleDateFormat formats[] = {
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
			new SimpleDateFormat("EEEEEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
			new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US) };

	// 请求的http头，名字是key，值是相应的是一个ArrayList
	protected HashMap headers = new HashMap();

	// 请求中解析到的参数
	protected ParameterMap parameters = null;

	// 这个请求是否已经解析过
	protected boolean parsed = false;
	protected String pathInfo = null;

	// 通过getReader方法给出
	protected BufferedReader reader = null;

	// 通过getInputStream方法给出
	protected ServletInputStream stream = null;

	// 构造，通过一个输入流构造
	public HttpRequest(InputStream input) {
		this.input = input;
	}

	// 请求头的解析
	public void addHeader(String name, String value) {
		name = name.toLowerCase();
		synchronized (headers) {
			ArrayList values = (ArrayList) headers.get(name);
			if (values == null) {
				values = new ArrayList();
				headers.put(name, values);
			}
			values.add(value);
		}
	}

	// 解析请求中的参数，如果还没有解析过。
	// 如果请求参数既存在查询字符串中和请求体中，那么这个两个参数会合并
	protected void parseParameters() {
		if (parsed)
			return;
		ParameterMap results = parameters;
		if (results == null)
			results = new ParameterMap();
		results.setLocked(false);
		String encoding = getCharacterEncoding();
		if (encoding == null)
			encoding = "ISO-8859-1";

		// 解析请求中任何的查询参数，也就是跟在请求路径后面的数据
		String queryString = getQueryString();
		try {
			RequestUtil.parseParameters(results, queryString, encoding);
		} catch (UnsupportedEncodingException e) {
			;
		}

		// 解析请求输入流中的任何请求参数
		String contentType = getContentType();
		if (contentType == null)
			contentType = "";
		int semicolon = contentType.indexOf(';');
		if (semicolon >= 0) {
			contentType = contentType.substring(0, semicolon).trim();
		} else {
			contentType = contentType.trim();
		}
		if ("POST".equals(getMethod()) && (getContentLength() > 0)
				&& "application/x-www-form-urlencoded".equals(contentType)) {
			try {
				int max = getContentLength();
				int len = 0;
				byte buf[] = new byte[getContentLength()];
				ServletInputStream is = getInputStream();
				while (len < max) {
					int next = is.read(buf, len, max - len);
					if (next < 0) {
						break;
					}
					len += next;
				}
				is.close();
				if (len < max) {
					throw new RuntimeException("Content length mismatch");
				}
				RequestUtil.parseParameters(results, buf, encoding);
			} catch (UnsupportedEncodingException e) {
				;
			} catch (IOException e) {
				throw new RuntimeException("Content read fail");
			}
		}

		// 存储最终的结果
		results.setLocked(true);
		parsed = true;
		parameters = results;
	}

	public void addCookies(Cookie cookie) {
		synchronized (cookies) {
			cookies.add(cookie);
		}
	}

	public ServletInputStream createInputStream() throws IOException {
		return new RequestStream(this);
	}

	public InputStream getStream() {
		return input;
	}

	public void setContentLength(int length) {
		this.contentLength = length;
	}

	public void setContentType(String type) {
		this.contentType = type;
	}

	public void setInet(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	public void setContextPath(String path) {
		if (path == null) {
			this.contextPath = "";
		} else {
			this.contextPath = path;
		}
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setPathInfo(String path) {
		this.pathInfo = path;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	// 设置访问这个服务的地址（virtual host）
	public void setServerName(String name) {
		this.serverName = name;
	}

	public void setServerPort(int port) {
		this.serverPort = port;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setRequestedSessionCookie(boolean flag) {
		this.requestedSessionCookie = flag;
	}

	public void setRequestedSessionId(String requestedSessionId) {
		this.requestedSessionId = requestedSessionId;
	}

	public void setRequestedSessionURL(boolean flag) {
		requestedSessionURL = flag;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	/*----------接口中的方法----------*/
	public Object getAttribute(String name) {
		synchronized (attributes) {
			return attributes.get(name);
		}
	}

	public Enumeration getAttributeNames() {
		synchronized (attributes) {
			return new Enumerator(attributes.keySet());
		}
	}

	public String getCharacterEncoding() {
		return null;
	}

	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {
	}

	public int getContentLength() {
		return contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public ServletInputStream getInputStream() throws IOException {
		if(reader != null){
			throw new IllegalStateException("getInputStream has been called");
		}
		if(stream == null){
			stream = createInputStream();
		}
		return stream;
	}

	public String getParameter(String name) {
		parseParameters();
		String values[] = (String[]) parameters.get(name);
		if(values != null){
			return values[0];
		} else {
			return null;
		}
	}

	public Enumeration getParameterNames() {
		parseParameters();
		return new Enumerator(parameters.keySet());
	}

	public String[] getParameterValues(String name) {
		parseParameters();
		String values[] = (String[]) parameters.get(name);
		if (values != null) {
			return values;
		} else {
			return null;
		}
	}

	public Map getParameterMap() {
		parseParameters();
		return this.parameters;
	}

	public String getProtocol() {
		return protocol;
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
		if (stream != null) {
			throw new IllegalStateException("getInputStream has been called.");
		}
		if (reader == null) {
			String encoding = getCharacterEncoding();
			if (encoding == null) {
				encoding = "ISO-8859-1";
			}
			InputStreamReader isr = new InputStreamReader(createInputStream(),
					encoding);
			reader = new BufferedReader(isr);
		}
		return reader;
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
		synchronized (cookies) {
			if (cookies.size() < 1) {
				return null;
			}
			Cookie results[] = new Cookie[cookies.size()];
			return (Cookie[]) cookies.toArray(results);
		}
	}

	public long getDateHeader(String name) {
		String value = getHeader(name);
		if (value == null) {
			return (-1L);
		}
		value += " ";
		for (int i = 0; i < formats.length; i++) {
			try {
				Date date = formats[i].parse(value);
				return date.getTime();
			} catch (ParseException e) {
				;
			}
		}
		throw new IllegalArgumentException(value);
	}

	public String getHeader(String name) {
		name = name.toLowerCase();
		synchronized (headers) {
			ArrayList values = (ArrayList) headers.get(name);
			if (values != null) {
				return (String) values.get(0);
			} else {
				return null;
			}
		}
	}

	public Enumeration getHeaders(String name) {
		name = name.toLowerCase();
		synchronized (headers) {
			ArrayList values = (ArrayList) headers.get(name);
			if (values != null)
				return (new Enumerator(values));
			else
				return (new Enumerator(empty));
		}
	}

	public Enumeration getHeaderNames() {
		synchronized (headers) {
			return (new Enumerator(headers.keySet()));
		}
	}

	public int getIntHeader(String name) {
		String value = getHeader(name);
		if (value == null)
			return (-1);
		else
			return (Integer.parseInt(value));
	}

	public String getMethod() {
		return method;
	}

	public String getPathInfo() {
		return pathInfo;
	}

	public String getPathTranslated() {
		return null;
	}

	public String getContextPath() {
		return contextPath;
	}

	public String getQueryString() {
		return queryString;
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
