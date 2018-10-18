package com.chinatvpay.decorate;

// 顶级接口
public abstract class Beverage {
	String description = "Unknown Beverage";

	public String getDescription() {
		return description;
	}
	
	public abstract double cost();
}
