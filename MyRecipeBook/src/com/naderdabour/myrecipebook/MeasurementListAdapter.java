package com.naderdabour.myrecipebook;

import java.util.List;

import com.naderdabour.myrecipebook.viewmodels.MeasurementVM;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MeasurementListAdapter extends ArrayAdapter<MeasurementVM> {

	private Context context;
	public MeasurementListAdapter(Context context, List<MeasurementVM> measurements) {
		super(context, android.R.layout.simple_list_item_1, measurements);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.measurement_list_entry, null);
		MeasurementVM measurement = getItem(position);
		
		TextView measurementIdTextView = (TextView) view.findViewById(R.id.measurementIdTextView);
		TextView measurementNameTextView = (TextView) view.findViewById(R.id.measurementNameTextView);

		measurementIdTextView.setText(Long.toString(measurement.getId()));
		measurementNameTextView.setText(measurement.getName());

		return view;
	}
}
