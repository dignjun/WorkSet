package com.chinatvpay.decorate;

// 饮料
public class HouseBlend extends Beverage {

	public HouseBlend() {
		description = "HouseBlend";
	}
	
	@Override
	public double cost() {
		return 0.89;
	}

}
