package com.naderdabour.myrecipebook.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class RecipeFullVM {
	
	private long id;
	private String name;
	private CategoryVM category;
	private String details;
	private String image;
	private List<IngredientVM> ingredients;
	
	public RecipeFullVM(){
		this.ingredients = new ArrayList<IngredientVM>();
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

	public List<IngredientVM> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientVM> ingredients) {
		this.ingredients = ingredients;
	}
}
