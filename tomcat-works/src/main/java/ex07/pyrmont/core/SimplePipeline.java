package ex07.pyrmont.core;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

public class SimplePipeline implements Pipeline, Lifecycle{

	protected Valve basic = null;
	protected Container container = null;
	protected Valve[] valves = new Valve[0];
	
	public SimplePipeline(Container container){
		setContainer(container);
	}
	
	public void setContainer(Container container){
		this.container = container;
	}
	
	public void addLifecycleListener(LifecycleListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public LifecycleListener[] findLifecycleListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeLifecycleListener(LifecycleListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void start() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	public void stop() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	// 添加一个
	public void addValve(Valve valve) {
		if(valve instanceof Contained)
			((Contained) valve).setContainer(this.container);
		synchronized (valves) {
			Valve[] results = new Valve[valves.length + 1];
			System.arraycopy(valves, 0, results, 0, valves.length);
			results[valves.length] = valve;
			valves = results;
		}
	}

	public Valve getBasic() {
		return basic;
	}

	public Valve[] getValves() {
		return valves;
	}

	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		(new StandardPipelineValveContext()).invokeNext(request, response);
	}

	public void removeValve(Valve arg0) {
		// TODO Auto-generated method stub
		
	}

	// 在设置的阀门的时候同时关联相关的容器
	public void setBasic(Valve valve) {
		this.basic = valve;
		((Contained) valve).setContainer(container);
	}
	
	protected class StandardPipelineValveContext implements ValveContext{
		protected int stage = 0;
		public String getInfo() {
			return null;
		}

		public void invokeNext(Request request, Response response) throws IOException,
				ServletException {
			int subscript = stage;
			stage = stage + 1;
			if(subscript < valves.length){
				valves[subscript].invoke(request, response, this);
			}else if((subscript == valves.length) && (basic != null)){
				basic.invoke(request, response, this);
			}else{
				throw new ServletException("No valve");
			}
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
