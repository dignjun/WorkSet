package ex07.pyrmont.core;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

/**
 * 生命周期监听器
 * 
 * @author DINGJUN
 *
 */
public class SimpleContextLifecycleListener implements LifecycleListener{

	@SuppressWarnings("unused")
	public void lifecycleEvent(LifecycleEvent event) {
		Lifecycle lifecycle = event.getLifecycle();
		System.out.println("SimpleContextLifecycleListener's event" + event.getType().toString());
		// 上面的unused可以将下面的判断中Lifecycle类换成lifecycle变量。都是字符串，从哪里获取的好像并不重要，而且绝对是一样的。
		if(Lifecycle.START_EVENT.equals(event.getType())){
			System.out.println("Starting context.");
		}else if(Lifecycle.STOP_EVENT.equals(event.getType())){
			System.out.println("Stopping context.");
		}
	}

}



























