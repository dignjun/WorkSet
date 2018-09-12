package com.chinatvpay.observe;
/**
 * 观察者接口
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @author DINGJUN
 *
 */
public interface Observe {
	
	public void update(float temp, float humidity, float pressure);
	
}
