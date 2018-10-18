package com.chinatvpay.decorate;

public class DarkRoast extends Beverage {

	public DarkRoast() {
		description = "DarkRoast";
	}

	@Override
	public double cost() {
		return 2.33;
	}

}
