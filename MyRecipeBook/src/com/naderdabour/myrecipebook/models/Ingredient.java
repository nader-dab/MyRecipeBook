package com.naderdabour.myrecipebook.models;

public class Ingredient {

	private int ingredientId;
	private Product product;
	private Measurement measurement;
	private int quantity;
	
	public int getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
