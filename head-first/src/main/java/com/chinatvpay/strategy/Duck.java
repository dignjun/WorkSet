package com.chinatvpay.strategy;
/**
 * 鸭子顶级接口
 * 这个抽线顶级父类开始的设计就拥有飞行和叫的行为，使用继承设计鸭子体系的显然是有缺陷的，扩展的时候出现了问题了，
 * 例如一个普通的鸭子是会叫的，而橡皮鸭是不会叫的，但是继承的是同一个父类，那么他们的行为就是一样的了，
 * 显然是不合理，所以我们需要把变化给封装起来，也就是不同的鸭子的行为是不同的，但是会有这么个行为，所以就抽象出了
 * 行为的顶级接口，不同的鸭子的行为实现不同的接口，而鸭子类本身就不用管这么多了，只需要引用顶级抽象接口的引用就可以了。
 * 
 * 
 * 策略模式：策略模式定义了算法簇，分别封装起来，让它们之间可以相互替换，此模式让算法的变化独立于使用算法的客户（java中的加解密体系？？？）。
 * 
 * 设计原则：针对接口编程，而不是针对实现编程。
 * 设计原则：多用组合，少用继承。
 * 
 * @author DINGJUN
 *
 */
public abstract class Duck {
	
	// 这里给行为声明顶级接口引用。
	FlyBehavior flyBehavior;
	QuackBehavior quackBehavior;
	
	public Duck() {}
	
	public abstract void display();
	
	public void performFly() {
		// 委托给行为类
		flyBehavior.fly();
	}
	public void performQuack() {
		// 委托给行为类
		quackBehavior.quack();
	}
	public void swim() {
		System.out.println("所有的鸭子都是会游泳的，即使是玩具鸭子");
	}
	
	// 为鸭子“动态的设定行为”。这样没有在构造中初始化这两个行为，后续的操作中也是可以通过set方法为鸭子类设置行为。
	public void setFlyBehavior(FlyBehavior fb) {
		this.flyBehavior = fb;
	}
	public void setQuackBehavior(QuackBehavior qb) {
		this.quackBehavior = qb;
	}
}
