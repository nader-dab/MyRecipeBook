package com.naderdabour.myrecipebook.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.naderdabour.myrecipebook.models.Measurement;

public class MeasurementDatasource extends GenericDatasource<Measurement> {

	private static final String[] allColumns = {
		DatabaseHelper.TABLE_MEASUREMENT_ID,
		DatabaseHelper.TABLE_MEASUREMENT_NAME
	};
	
	public MeasurementDatasource(Context context) {
		super(context, DatabaseHelper.TABLE_MEASUREMENT, allColumns);
	}

	@Override
	protected ContentValues entryToContentValues(Measurement entry) {
		ContentValues values = new ContentValues();
		
		values.put(DatabaseHelper.TABLE_MEASUREMENT_NAME, entry.getName());
		
		return values;
	}
	
	@Override
	protected long getEntryId(Measurement entry) {
		
		return entry.getId();
	}
	
	@Override
	protected void setEntryId(Measurement entry, long insertId) {
		entry.setId(insertId);
	}

	@Override
	protected List<Measurement> cursorToList(Cursor cursor) {
		List<Measurement> measurements = new ArrayList<Measurement>();
		if(cursor.getCount() > 0){
			
			while (cursor.moveToNext()) {
				Measurement measurement = new Measurement();
				measurement.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_MEASUREMENT_ID)));
				measurement.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TABLE_MEASUREMENT_NAME)));
				measurements.add(measurement);
			}
		}
		return measurements;
	}
	
}
