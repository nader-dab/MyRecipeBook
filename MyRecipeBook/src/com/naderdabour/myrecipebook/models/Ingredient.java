package com.naderdabour.myrecipebook.models;

public class Ingredient {

	private long id;
	private double quantity;
	private long measurementId;
	private long productId;
	private long recipeId;
	
	public Ingredient() {
	}
	
	public Ingredient(long id, double quantity, long measurementId, 
			long productId, long recipeId) {
		this.id = id;
		this.productId = productId;
		this.measurementId = measurementId;
		this.quantity = quantity;
		this.recipeId = recipeId;
	}
	public long getId() {
		return id;
	}
	public void setId(long ingredientId) {
		this.id = ingredientId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getMeasurementId() {
		return measurementId;
	}
	public void setMeasurementId(long measurementId) {
		this.measurementId = measurementId;
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
			return this.quantity + " measurementId " + this.measurementId + " productId " + this.productId;
	}
}

