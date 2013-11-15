package com.naderdabour.myrecipebook.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class RecipeFullVM {
	
	private long id;
	private String name;
	private CategoryVM category;
	private String image;
	private List<IngredientVM> ingredients;
	private String details;
	
	public RecipeFullVM(){
		this.ingredients = new ArrayList<IngredientVM>();
	}

	public RecipeFullVM(long id, String name, CategoryVM category,
			String image, List<IngredientVM> ingredients, String details) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.image = image;
		this.ingredients = ingredients;
		this.details = details;
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
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.category + "\n");
		sb.append(this.name + "\n");
		sb.append(this.image + "\n");
		
		for (IngredientVM ingredient : this.ingredients) {
			
			sb.append(ingredient + "\n");
		}

		sb.append(this.details + "\n");
		
		return sb.toString();
	}
}
