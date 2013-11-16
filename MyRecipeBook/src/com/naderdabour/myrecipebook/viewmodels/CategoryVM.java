package com.naderdabour.myrecipebook.viewmodels;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryVM implements Parcelable {
	
	private long id;
	private String name;
	
	public CategoryVM() {
	}
	
	public CategoryVM(Parcel in){ 
		id = in.readLong();
		name = in.readString();
	}
	
	public CategoryVM(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long categoryId) {
		this.id = categoryId;
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
		dest.writeLong(id);
		dest.writeString(name);
	}
	
	public static final Parcelable.Creator<CategoryVM> CREATOR = new Parcelable.Creator<CategoryVM>(){

		@Override
		public CategoryVM createFromParcel(Parcel source) {
			
			return new CategoryVM(source);
		}

		@Override
		public CategoryVM[] newArray(int size) {
			
			return new CategoryVM[size];
		}
	};

	
}
