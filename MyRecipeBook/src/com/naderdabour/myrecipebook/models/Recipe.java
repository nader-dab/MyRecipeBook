package com.naderdabour.myrecipebook.models;

import java.util.ArrayList;
import java.util.List;


public class Recipe {

	private long id;
	private String name;
	private Category category;
	private List<Ingredient> ingredients;
	private String details;
	private String image;
	
	public Recipe(){
		ingredients = new ArrayList<Ingredient>();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long recipeId) {
		this.id = recipeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
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
		
		sb.append(this.category + "\n");
		
		for (Ingredient ingredient : this.ingredients) {
			
			sb.append(ingredient + "\n");
		}
		
		if (this.details.length() != 0) {
			
			sb.append(this.details + "\n");
		}
		
		return sb.toString();
	}
}
