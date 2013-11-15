package com.naderdabour.myrecipebook.models;

public class Product {

	private long id;
	private String name;
	
	public Product() {
	}
	
	public Product(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long productId) {
		this.id = productId;
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