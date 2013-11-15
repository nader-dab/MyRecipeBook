package com.naderdabour.myrecipebook.viewmodels;

public class IngredientVM {

	private long id;
	private double quantity;
	private MeasurementVM measurement;
	private ProductVM product;
	
	public IngredientVM() {
	}
	
	public IngredientVM(long id, double quantity, MeasurementVM measurement,
			ProductVM product) {
		this.id = id;
		this.quantity = quantity;
		this.measurement = measurement;
		this.product = product;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ProductVM getProduct() {
		return product;
	}
	public void setProduct(ProductVM product) {
		this.product = product;
	}
	public MeasurementVM getMeasurement() {
		return measurement;
	}
	public void setMeasurement(MeasurementVM measurement) {
		this.measurement = measurement;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		
		return this.quantity + " " + this.measurement + " " + this.product;
	}
}
