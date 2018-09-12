package com.chinatvpay.strategy;
/**
 * 可以飞行的鸭子，如：家禽小鸭子
 * @author DINGJUN
 *
 */
public class FlyWithWings implements FlyBehavior{

	@Override
	public void fly() {
		System.out.println("使用翅膀飞行的鸭子");
	}
}
