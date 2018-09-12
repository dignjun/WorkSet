package com.chinatvpay.strategy;

/**
 * 具体的鸭子类，唐老鸭。
 * @author DINGJUN
 *
 */
public class MallardDuck extends Duck {

	// 构造中初始化这个鸭子的具体行为
	public MallardDuck() {
		quackBehavior = new Quack();
		flyBehavior = new FlyWithWings();
	}
	
	@Override
	public void display() {
		System.out.println("我是一个唐老鸭");
	}

}
