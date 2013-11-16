package com.naderdabour.myrecipebook.viewmodels;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeSimpleVM implements Parcelable {
	private long id;
	private String name;
	private CategoryVM category;
	private String image;
	
	public RecipeSimpleVM() {
	}
	
	public RecipeSimpleVM(long id, String name, CategoryVM category,
			String image) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.image = image;
	}
	
	public RecipeSimpleVM(Parcel in){
		this.id = in.readLong();
		this.name = in.readString();
		this.category = in.readParcelable(CategoryVM.class.getClassLoader());
		this.image = in.readString();
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
	
		return this.category + " " + this.name + " " + this.image;
	}

	@Override
	public int describeContents() {
	
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeParcelable(this.category, flags);
		dest.writeString(this.image);
	}
	
	public static final Parcelable.Creator<RecipeSimpleVM> CREATOR = new Creator<RecipeSimpleVM>() {
		
		@Override
		public RecipeSimpleVM[] newArray(int size) {
			// TODO Auto-generated method stub
			return new RecipeSimpleVM[size];
		}
		
		@Override
		public RecipeSimpleVM createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new RecipeSimpleVM(source);
		}
	};
}
