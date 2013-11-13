package com.naderdabour.myrecipebook.models;

public class Measurement implements INameable {

	private int meausermentId;
	private String name;
	
	public int getMeausermentId() {
		return meausermentId;
	}
	public void setMeausermentId(int meausermentId) {
		this.meausermentId = meausermentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}

