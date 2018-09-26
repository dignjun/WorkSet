package ex05.pyrmont.core;

import java.io.IOException;

import javax.servlet.ServletException;





import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

/**
 * 一个流水线就像是一个过滤链，每一个阀门像是一个过滤器。跟过滤器一样，一个阀门可以操作传递给他的request和response方法。
 * 让一个阀门完成了处理，则进一步处理流水线中的下一个阀门，基本法门总是在对吼才被调用的。
 * 一个pipeline包含了该容器要唤醒的所有任务。每一个阀门表示一个特定的任务。一个容器的流水线有一个基本的阀门。
 * 一个容器可以有一个流水线。当容器的invoke方法方法被调用的时候，容器将会处理流水线中的阀门，并一个接一个的处理，直到所有的阀门都被处理。
 * 
 * @author DINGJUN
 *
 */
public class SimplePipeline implements Pipeline{

	// 基本的阀门。
	protected Valve basic = null;
	// 流水线持有的容器引用，也就是关联的容器。
	protected Container container = null;
	// 任务链。
	protected Valve valves[] = new Valve[0];
	
	// 构造，显示这个流水线和那个容器进行关联。
	public SimplePipeline(Container container){
		setContainer(container);
	}
	// setter方法
	public void setContainer(Container container){
		this.container = container;
	}
	
	public void addValve(Valve valve) {
		// Valve阀门接口实现者不仅实现了Valve接口，同时也实现了Contained接口，后者的接口的实现是可选的操作。
		// 我们实现的SimpleWrapperValve就是实现了这两个接口，也就是说这个阀门接口是一个特定的任务。
		if(valve instanceof Contained){
			// 因为实现Contained接口是可选操作，所以这里判断一下。
			((Contained)valve).setContainer(this.container);
		}
		synchronized (valves) {
			// 添加阀门，每次添加都是构建一个新的Valve[]数组出来。
			Valve results[] = new Valve[valves.length+1];
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

	// 流水线的invoke方法签名和容器接口的invoke方法签名是一致的。
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		(new SimplePipelineValveContext()).invokeNext(request, response);
	}

	public void removeValve(Valve valve) {
		
	}

	public void setBasic(Valve valve) {
		this.basic = valve;
		((Contained)valve).setContainer(container);
	}

	/**
	 * 流水线必须保证要给添加给它的阀门必须被调用一次，流水线通过创建一个ValveContext接口的实例来实现它。
	 * ValveContext是流水线的内部类，这样ValveContext就可以访问流水线中所有的成员。
	 * 这个类最重要的方法是invokeNext方法。
	 * 这个实例创建之后，流水线调用这个重要的方法。ValveContext会先唤醒流水线的第一个阀门，然后第一个阀门在完成它的任务之前唤醒下一个阀门。
	 * Valve将它自己传递给每一个阀门，那么阀门就可以调用ValveContext的invokeNext方法了。
	 * 
	 * ValveContext是阀门上下文接口
	 * 
	 * @author DINGJUN
	 *
	 */
	protected class SimplePipelineValveContext implements ValveContext{
		// 级别
		protected int stage = 0;
		public String getInfo() {
			return null;
		}
		// 第一次被唤醒的时候，下标是0，级的值是1.
		public void invokeNext(Request request, Response response) throws IOException,
				ServletException {
			// 下标
			int subscript = stage;
			stage = stage + 1;
			/*
			 * 疑问是：如何将多个阀门都执行的？
			 * 这里可能还是不完善的，按照应用目前的布局，这里目前是只有一个阀门的，也就是SimpleWrapperValve。
			 * 按照代码的流向，执行到这里之后会执行basic.invoke(request, response, this);
			 * 因为这个最后一个阀门了（basic）。然后执行之后这个阀门之后有关流水线就执行完毕了，如果其他的阀门也是这样的话，
			 * 哪来的循环执行，确保所有的Valve都会被执行，所以我想是在SimpleWrapperValve的invoke方法中根据
			 * this关键字传递的这个SimplePipelineValveContext的自身引用再次调用这个方法的。这样就会再次执行
			 * 这个方法了。
			 * 
			 * */
			if(subscript < valves.length){
				valves[subscript].invoke(request, response, this);
			} else if ((subscript == valves.length) && (basic != null)) {
				basic.invoke(request, response, this);
			} else {
				throw new ServletException("No valve");
			}
		}
	} // end of inner class
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
