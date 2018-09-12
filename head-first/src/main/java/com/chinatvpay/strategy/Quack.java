package com.chinatvpay.strategy;
/**
 * 可以呱呱叫的鸭子，如：家禽小鸭子
 * @author DINGJUN
 *
 */
public class Quack implements QuackBehavior {

	@Override
	public void quack() {
		System.out.println("呱呱叫");
	}

}
