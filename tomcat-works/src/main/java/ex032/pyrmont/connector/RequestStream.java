package ex032.pyrmont.connector;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;

import org.apache.catalina.util.StringManager;

import ex032.pyrmont.connector.http.Constants;
import ex032.pyrmont.connector.http.HttpRequest;
/**
 * Convenience implementation of <b>ServletInputStream</b> that works with
 * the standard implementations of <b>Request</b>.  If the content length has
 * been set on our associated Request, this implementation will enforce
 * not reading more than that many bytes on the underlying stream.
 * 
 * 
 * 
 * @author DINGJUN
 *
 */
public class RequestStream extends ServletInputStream{

	// 实例变量
	protected boolean closed = false;
	protected int count = 0;
	protected int length = -1;
	
	protected static StringManager sm = StringManager.getManager(Constants.Package);
	// 读取数据所依赖使用的输入流
	protected InputStream stream = null;
	
	// 构造
	public RequestStream(HttpRequest request) {
		super();
		closed = false;
		count = 0;
		length = request.getContentLength();
		stream = request.getStream();
		
	}
	
	public void close() throws IOException {
		if(closed) {
			throw new IOException(sm.getString("requestStream.close.closed"));
		}
		if(length > 0){
			while(count < length){
				int b = read();
				if(b < 0){
					break;
				}
			}
		}
		closed = true;
	}
	
	
	@Override
	public int read() throws IOException {
		
		if(closed) {
			throw new IOException(sm.getString("requestStream.read.closed"));
		}
		if((length >= 0) && (count >= length))
			return (-1);
		int b = stream.read();
		if(b >= 0){
			count ++;
		}
		return (b);
	}

	public int read(byte b[]) throws IOException{
		return (read(b, 0, b.length));
	}
	
	public int read(byte b[], int off, int len) throws IOException {

        int toRead = len;
        if (length > 0) {
            if (count >= length)
                return (-1);
            if ((count + len) > length)
                toRead = length - count;
        }
        int actuallyRead = super.read(b, off, toRead);
        return (actuallyRead);

    }
}
























