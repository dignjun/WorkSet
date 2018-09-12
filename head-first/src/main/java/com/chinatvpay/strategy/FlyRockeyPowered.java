package com.chinatvpay.strategy;
/**
 * 新的飞行行为：火箭飞行
 * @author DINGJUN
 *
 */
public class FlyRockeyPowered implements FlyBehavior {

	@Override
	public void fly() {
		System.out.println("我可以使用飞箭飞行");
	}
}
