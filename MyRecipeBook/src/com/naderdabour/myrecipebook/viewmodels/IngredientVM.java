package com.naderdabour.myrecipebook.viewmodels;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientVM implements Parcelable {

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
	
	public IngredientVM(Parcel in){
		this.id = in.readLong();
		this.quantity = in.readDouble();
		this.measurement = in.readParcelable(MeasurementVM.class.getClassLoader());
		this.product = in.readParcelable(ProductVM.class.getClassLoader());
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeLong(this.id);
		dest.writeDouble(this.quantity);
		dest.writeParcelable(this.measurement, flags);
		dest.writeParcelable(this.product, flags);
	}
	
	public static final Parcelable.Creator<IngredientVM> CREATOR = new Creator<IngredientVM>() {
		
		@Override
		public IngredientVM[] newArray(int size) {
		
			return new IngredientVM[size];
		}
		
		@Override
		public IngredientVM createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new IngredientVM(source);
		}
	};
}
