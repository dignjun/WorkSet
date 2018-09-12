package com.chinatvpay.strategy;
/**
 * 沉默的鸭子，如：橡皮鸭
 * @author DINGJUN
 *
 */
public class MuteQuack implements QuackBehavior {

	@Override
	public void quack() {
		System.out.println("沉默的鸭子");
	}

}
