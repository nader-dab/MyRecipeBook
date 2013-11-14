package com.naderdabour.myrecipebook.models;


public class Ingredient {

	private long id;
	private Product product;
	private Measurement measurement;
	private double quantity;
	private long recipeId;
	
	public long getId() {
		return id;
	}
	public void setId(long ingredientId) {
		this.id = ingredientId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Measurement getMeasurement() {
		return measurement;
	}
	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public long getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}
	@Override
	public String toString() {
		
		if (this.measurement.toString().length() == 0) {
			
			return this.quantity +  " " + this.product;
			
		} else {
			
			return this.quantity + " " + this.measurement + " " + this.product;
		}
	}
}

