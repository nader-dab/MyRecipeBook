package com.naderdabour.myrecipebook.viewmodels;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductVM implements Parcelable{
	private long id;
	private String name;
	
	public ProductVM() {
	}
	
	public ProductVM(Parcel in){
		this.id = in.readLong();
		this.name = in.readString();
	}
	
	public ProductVM(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long productId) {
		this.id = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int describeContents() {
		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeLong(this.id);
		dest.writeString(this.name);
	}
	
	public static final Parcelable.Creator<ProductVM> CREATOR = new Creator<ProductVM>() {
		
		@Override
		public ProductVM[] newArray(int size) {
			
			return new ProductVM[size];
		}
		
		@Override
		public ProductVM createFromParcel(Parcel source) {

			return new ProductVM(source);
		}
	};
}
