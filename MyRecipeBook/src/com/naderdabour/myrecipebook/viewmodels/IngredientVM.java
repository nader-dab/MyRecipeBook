package com.naderdabour.myrecipebook.viewmodels;

public class IngredientVM {

	private long id;
	private ProductVM product;
	private MeasurementVM measurement;
	private double quantity;
	
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
		
		return quantity + " " + measurement.getName() + " " + product.getName();
	}
}
