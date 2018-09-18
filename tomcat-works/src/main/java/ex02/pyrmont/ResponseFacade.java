package ex02.pyrmont;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 * servletresponse的门面类，同样是为了解决转型的问题，而转型是为了保护实现类中的特有方法
 * 
 * @author DINGJUN
 *
 */
public class ResponseFacade implements ServletResponse{
	// 持有实现的接口引用
	private ServletResponse response;
	// 在构造中将接口的引用指向实现的引用，完成向上转型
	public ResponseFacade(Response response) {
		this.response = response;
	}
	/*实现全部是接口的实现*/
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
}
