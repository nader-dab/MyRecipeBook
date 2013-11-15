package com.naderdabour.myrecipebook.models;

public class Recipe {

	private long id;
	private long categoryId;
	private String name;
	private String details;
	private String image;
	
	public Recipe() {
	}
	
	public Recipe(long id, long categoryId, String name, String details,
			String image) {
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.details = details;
		this.image = image;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
	
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.name + "\n");
		
		sb.append(this.categoryId + "\n");

		if (this.details.length() != 0) {
			
			sb.append(this.details + "\n");
		}
		
		return sb.toString();
	}
}
