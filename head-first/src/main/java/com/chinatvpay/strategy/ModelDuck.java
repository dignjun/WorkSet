package com.chinatvpay.strategy;

/**
 * 鸭子具体类，模型鸭
 * @author DINGJUN
 *
 */
public class ModelDuck extends Duck {
	public ModelDuck() {
		flyBehavior = new FlyNoWay();
		quackBehavior = new Quack();
	}
	@Override
	public void display() {
		System.out.println("我是一个模型鸭子");
	}

}
