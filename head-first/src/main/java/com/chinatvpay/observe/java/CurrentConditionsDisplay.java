package com.chinatvpay.observe.java;

import java.util.Observable;
import java.util.Observer;

import com.chinatvpay.observe.DisplayElement;

/**
 * jdk中实现的观察者模式的观察者
 * 需要实现Observer接口，
 * 然后调用任何的主题对象的添加观察者对象方法就可以了
 * 
 * @author DINGJUN
 *
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement{

	Observable observable;
	private float temperature;
	private float humidity;
	
	public CurrentConditionsDisplay(Observable observable) {
		this.observable = observable;
		observable.addObserver(this);
	}
	
	// 这个对象将主题也作为一个参数传递给观察者，这样，观察者就知道是哪个主题通知的它
	// 第二个参数就是传入观察者的数据对象，如果没有就是空。
	// 这里其实有点疑惑，这里已经持有主题的引用，那么变化的数据就已经可以获取到了，那么，后面的参数使用又是什么样的场景？？？
	@Override
	public void update(Observable o, Object arg) {
		// 这里可以指定针对某一个观察者进行操作。
		if(o instanceof WeatherData) {
			WeatherData weatherData = (WeatherData) o;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();
		}
	}

	@Override
	public void display() {
		System.out.println("current conditions:"+temperature+"F degrees and "+humidity+"% humidity");
	}

}
