package com.naderdabour.myrecipebook.viewmodels;

public class RecipeSimpleVM {
	private long id;
	private String name;
	private CategoryVM category;
	private String image;
	
	public RecipeSimpleVM() {
	}
	
	public RecipeSimpleVM(long id, String name, CategoryVM category,
			String image) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
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
	public CategoryVM getCategory() {
		return category;
	}
	public void setCategory(CategoryVM category) {
		this.category = category;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
	
		return this.category + " " + this.name + " " + this.image;
	}
}
