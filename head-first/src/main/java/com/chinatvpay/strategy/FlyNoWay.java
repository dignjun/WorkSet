package com.chinatvpay.strategy;

/**
 * 不会飞行的鸭子，如：橡皮鸭
 * 
 * @author DINGJUN
 *
 */
public class FlyNoWay implements FlyBehavior {

	@Override
	public void fly() {
		System.out.println("我并不会飞哦");
	}

}

