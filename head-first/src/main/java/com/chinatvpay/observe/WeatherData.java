package com.chinatvpay.observe;

import java.util.ArrayList;

/**
 * 观察者主题的实现，其实也就是被观察者。
 * 观察者的主题也就是众多观察者依赖的对象。
 * 
 * 
 * @author DINGJUN
 *
 */
public class WeatherData implements Subject {

	// 持有观察者对象列表。不然信息改变如何通知到观察者
	private ArrayList<Observe> observes;
	
	// 主题的三个属性，被观察者观察的元素
	private float temperature;
	private float humidity;
	private float pressure;

	// 构造初始化观察者列表
	public WeatherData() {
		observes = new ArrayList<Observe>();
	}
	
	@Override
	public void registerObserve(Observe observe) {
		observes.add(observe);
	}
	@Override
	public void removeObserve(Observe observe) {
		int i = observes.indexOf(observe);
		if(i > 0) {
			observes.remove(i);
		}
	}
	@Override
	public void notifyObserve() {
		for (int i = 0; i < observes.size(); i++) {
			Observe observe = observes.get(i);
			observe.update(temperature, humidity, pressure);
		}
	}
	public void measurementsChanged() {
		notifyObserve();
	}

	// 给主题设置属性
	public void setMessurements(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		// 当设置属性的时候就是主题改变的时候，在这里对观察者进行通知。问题是，为什么不直接调用notifyObserve（）方法，
		// 而要包装一层measurementsChanged（）的方法使用。
		measurementsChanged();
	}
	
}
