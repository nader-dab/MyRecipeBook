package com.naderdabour.myrecipebook.viewmodels;

public class MeasurementVM {

	private long id;
	private String name;
	
	public MeasurementVM() {
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
}
