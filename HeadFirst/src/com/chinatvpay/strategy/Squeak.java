package com.chinatvpay.strategy;

/**
 * 尖尖叫的鸭子，如：处在变声期的小鸭子QAQ
 * @author DINGJUN
 *
 */
public class Squeak implements QuackBehavior {

	@Override
	public void quack() {
		System.out.println("尖尖叫的鸭子");
	}

}
