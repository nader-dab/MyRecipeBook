package com.naderdabour.myrecipebook.viewmodels;

public class CategoryVM {
	
	private long id;
	private String name;
	
	public CategoryVM() {
	}
	
	public CategoryVM(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long categoryId) {
		this.id = categoryId;
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
