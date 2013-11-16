package com.naderdabour.myrecipebook.viewmodels;

import android.os.Parcel;
import android.os.Parcelable;

public class MeasurementVM implements Parcelable {

	private long id;
	private String name;
	
	public MeasurementVM() {
	}
	
	public MeasurementVM(Parcel in){
		
		this.id = in.readLong();
		this.name = in.readString();
	}
	
	public MeasurementVM(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long meausermentId) {
		this.id = meausermentId;
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
	
	public static final Parcelable.Creator<MeasurementVM> CREATOR = new Parcelable.Creator<MeasurementVM>() {
		
		@Override
		public MeasurementVM[] newArray(int size) {
			return new MeasurementVM[size];
		}
		
		@Override
		public MeasurementVM createFromParcel(Parcel source) {
			return new MeasurementVM(source);
		}
	};
}
