package com.naderdabour.myrecipebook.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.naderdabour.myrecipebook.models.Category;

public class CategoryDatasource extends GenericDatasource<Category> {

	
	private static final String[] allColumns = {
		DatabaseHelper.TABLE_CATEGORY_ID,
		DatabaseHelper.TABLE_CATEGORY_NAME
	};
	
	
	public CategoryDatasource(Context context) {
		super(context, DatabaseHelper.TABLE_CATEGORY, allColumns);
	}

	@Override
	protected ContentValues entryToContentValues(Category entry) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.TABLE_CATEGORY_NAME, entry.getName());
		return values;
	}
	
	@Override
	protected void setEntryId(Category entry, long insertId) {
		entry.setId(insertId);
	}
	
	@Override
	protected long getEntryId(Category entry) {
		return entry.getId();
	}


	protected List<Category> cursorToList(Cursor cursor) {
		List<Category> categories = new ArrayList<Category>();
		if(cursor.getCount() > 0){
			
			while (cursor.moveToNext()) {
				
				long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_CATEGORY_ID));
				String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TABLE_CATEGORY_NAME));
				
				Category category = new Category(id, name);
				categories.add(category);
			}
		}
		return categories;
	}

}
