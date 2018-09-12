package com.chinatvpay.observe;
/**
 * 观察者模式：观察者模式定义了对象之间的一对多的依赖，这样一来，当一个对象改变时，他的所有依赖者都会收到通知并且自动更新。
 * 
 * 观察者模式中的主题
 * 
 * 
 * 
 * 
 * 
 * 
 * @author DINGJUN
 *
 */
public interface Subject {

	public void registerObserve(Observe observe);
	public void removeObserve(Observe observe);
	public void notifyObserve();
	
}
