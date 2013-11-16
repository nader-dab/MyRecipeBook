package com.naderdabour.myrecipebook.models;

public class Measurement {

	private long id;
	private String name;
	
	public Measurement() {
	}
	
	public Measurement(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long meausermentId) {
		this.id = meausermentId;
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