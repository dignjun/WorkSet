package ex06.pyrmont.core;

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

	// the basic Valve associated with this Pipeline
	protected Valve basic = null;
	// the Container with which this Pipeline is associated
	protected Container container = null;
	// the array of valves
	protected Valve valves[] = new Valve[0];
	
	// 构造
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

	public void addValve(Valve valve) {
		// 这第一个判断啥意思呢？其实和setBasic（）方法类似，添加一个Valve都是要设置关联的容器的
		// 不过我想如果不这样设置，那么在创建这个Valve的时候就需要特地的调用setContainer（）方法关联容器了
		if(valve instanceof Contained)
			((Contained)valve).setContainer(this.container);
		synchronized (valves) {
			Valve results[] = new Valve[valves.length + 1];
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

	public void setBasic(Valve valve) {
		this.basic = valve;
		// 关联相关的容器，如果在添加的时候没有进行这个关联操作，那么需要在调用这个方法之前进行容器的关联。
		((Contained)valve).setContainer(container);
	}
	
	// 实际处理的方法
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
