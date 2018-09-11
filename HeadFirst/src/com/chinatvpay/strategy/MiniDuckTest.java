package com.chinatvpay.strategy;
/**
 * 鸭子测试类。
 * @author DINGJUN
 *
 */
public class MiniDuckTest {
	public static void main(String[] args) {
		Duck mallard = new MallardDuck();
		mallard.performQuack();
		mallard.performFly();
		
		Duck model = new ModelDuck();
		model.performFly();
		// 动态的该修改鸭子的行为
		model.setFlyBehavior(new FlyRockeyPowered());
		model.performFly();
	}
}
