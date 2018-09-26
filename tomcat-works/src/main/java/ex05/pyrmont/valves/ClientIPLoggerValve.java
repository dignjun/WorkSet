package ex05.pyrmont.valves;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;
/**
 * 又一个阀门
 * 
 * @author DINGJUN
 *
 */
public class ClientIPLoggerValve implements Valve, Contained{

	protected Container container;
	
	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public String getInfo() {
		return null;
	}

	public void invoke(Request request, Response response, ValveContext valveContext)
			throws IOException, ServletException {
		
		/* pass this request on to the next valve in our pipeline
		 	这里的下一个执行放在最后会不会好一点，也就是这个阀门执行完成后再执行下一个
		 	上面也是好理解一点，如果都是这种写法，那么所有的阀门都将在这里阻塞，反而是base最先执行了，
		 	然后这些valve最后才执行，和之前说的base最后执行有很大的出入吧。
		*/
		valveContext.invokeNext(request, response);
		System.out.println("Clinet IP Logger Valve");
		ServletRequest sreq = request.getRequest();
		System.out.println(sreq.getRemoteAddr());
		System.out.println("---------------------------------");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
