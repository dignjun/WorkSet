package com.chinatvpay.observe;
/**
 * 气象台
 * 
 * @author DINGJUN
 *
 */
public class WeatherStationTest {
	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
		/**
		 *  创建对象的时候已经注册了自己，引用装在了主题对象的观察者列表中，这个对象里面也有一个WeatherData对象引用，
		 *  指向上面的对象。有一个小小的疑问，那就是这里的这个观察者是否还是一个匿名对象。
		 */
		new CurrentConditionDisplay(weatherData);
		weatherData.setMessurements(80, 60, 30.4f);
		weatherData.setMessurements(20, 90, 37.29f);
		weatherData.setMessurements(46, 55, 26.87f);
		System.gc();
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			System.err.println("异常");
		}
		weatherData.setMessurements(11, 12, 13f);
	}
}
