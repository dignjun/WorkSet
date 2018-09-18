package ex03.pyrmont.connector.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
/**
 * response的门面类
 * 
 * @author DINGJUN
 *
 */
@SuppressWarnings("deprecation")
public class HttpResponseFacade implements HttpServletResponse{

	
	private HttpServletResponse response;
	
	public HttpResponseFacade(HttpServletResponse response){
		this.response = response;
	}
	
	public String getCharacterEncoding() {
		return response.getCharacterEncoding();
	}

	public String getContentType() {
		return response.getContentType();
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return response.getOutputStream();
	}

	public PrintWriter getWriter() throws IOException {
		return response.getWriter();
	}

	public void setCharacterEncoding(String charset) {
	}

	public void setContentLength(int len) {
	}

	public void setContentType(String type) {
	}

	public void setBufferSize(int size) {
	}

	public int getBufferSize() {
		return response.getBufferSize();
	}

	public void flushBuffer() throws IOException {
	}

	public void resetBuffer() {
	}

	public boolean isCommitted() {
		return response.isCommitted();
	}

	public void reset() {
	}

	public void setLocale(Locale loc) {
	}

	public Locale getLocale() {
		return response.getLocale();
	}

	public void addCookie(Cookie cookie) {
	}

	public boolean containsHeader(String name) {
		return response.containsHeader(name);
	}

	public String encodeURL(String url) {
		return response.encodeURL(url);
	}

	public String encodeRedirectURL(String url) {
		return response.encodeRedirectURL(url);
	}

	public String encodeUrl(String url) {
		return response.encodeUrl(url);
	}

	public String encodeRedirectUrl(String url) {
		return response.encodeRedirectUrl(url);
	}

	public void sendError(int sc, String msg) throws IOException {
	}

	public void sendError(int sc) throws IOException {
	}

	public void sendRedirect(String location) throws IOException {
	}

	public void setDateHeader(String name, long date) {
	}

	public void addDateHeader(String name, long date) {
	}

	public void setHeader(String name, String value) {
	}

	public void addHeader(String name, String value) {
	}

	public void setIntHeader(String name, int value) {
	}

	public void addIntHeader(String name, int value) {
	}

	public void setStatus(int sc) {
	}

	public void setStatus(int sc, String sm) {
	}

}
