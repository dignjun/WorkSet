package com.chinatvpay.observe.java;

import java.util.Observable;
/**
 * jdk中实现的观察者模式主题类
 * 主题需要继承这个Observable
 * 
 * 使用：首先需要标记状态已经改变的事实，也就是调用setChanged（）方法，然后两种通知方法的任意一个。
 * 
 * @author DINGJUN
 *
 */
public class WeatherData extends Observable{
	
	private float temperature;
	private float humidity;
	private float pressure;
	
	public WeatherData() {
	}
	
	public void measurementsChanged() {
		setChanged();
		notifyObservers();// 这里并不是调用传递参数的方法，说明这里的数据的获取是通过“拉取数据”
	}
	
	public void setMeasurements(float tempearture, float humidity, float pressure) {
		this.temperature = tempearture;
		this.humidity = humidity;
		this.pressure = pressure;
	}

	public float getTemperature() {
		return temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getPressure() {
		return pressure;
	}
	
	
}
