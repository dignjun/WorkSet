package com.chinatvpay.decorate;

public class Whip extends Condiment {

	Beverage beverage;
	
	public Whip(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return this.beverage.getDescription() + ", Whip";
	}

	@Override
	public double cost() {
		return 0.41 + this.beverage.cost();
	}

}
