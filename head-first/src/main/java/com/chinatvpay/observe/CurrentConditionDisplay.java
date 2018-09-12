package com.chinatvpay.observe;

/**
 * 其中的一个观察者
 * 
 * 
 * @author DINGJUN
 *
 */
public class CurrentConditionDisplay implements Observe, DisplayElement {

	private float temperature;
	private float humidity;
	@SuppressWarnings("unused")
	private Subject weatherData;
	
	// 这个构造相当nice，也就是在创建这个对象的时候就把自己注册了。而使用普通的构造呢？
	// 那就只能通过主题对象主动的去注册这个观察者了。有一个小疑问？就是为什么要让这个对象
	// 持有主题对象呢，直接通过构造方法的形参引用主题对象，完成自注册不就可以了吗？？？
	public CurrentConditionDisplay(Subject weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserve(this);
	}
	
	@Override
	public void display() {
		System.out.println("current conditions:"+temperature+"F degrees and "+humidity+"% humidity");
	}

	@Override
	public void update(float temp, float humidity, float pressure) {
		this.temperature = temp;
		this.humidity = humidity;
		display();
	}

}
