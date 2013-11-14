package com.naderdabour.myrecipebook.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.naderdabour.myrecipebook.models.Recipe;

public class RecipeDatasource extends GenericDatasource<Recipe> {

	private static final String[] allColumns = {
		DatabaseHelper.TABLE_RECIPE_ID,
		DatabaseHelper.TABLE_RECIPE_NAME,
		DatabaseHelper.TABLE_RECIPE_DETAILS,
		DatabaseHelper.TABLE_RECIPE_IMAGE,
		DatabaseHelper.TABLE_RECIPE_CATEGORY_ID
	};
	
	public RecipeDatasource(Context context) {
		super(context, DatabaseHelper.TABLE_RECIPE, allColumns);
	}
	
	@Override
	protected ContentValues entryToContentValues(Recipe entry) {
		
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.TABLE_RECIPE_NAME, entry.getName());
		values.put(DatabaseHelper.TABLE_RECIPE_DETAILS, entry.getDetails());
		values.put(DatabaseHelper.TABLE_RECIPE_IMAGE, entry.getImage());
		values.put(DatabaseHelper.TABLE_RECIPE_CATEGORY_ID, entry.getCategoryId());
		
		return values;
	}

	@Override
	protected long getEntryId(Recipe entry) {
		
		return entry.getId();
	}

	@Override
	protected void setEntryId(Recipe entry, long insertId) {
		
		entry.setId(insertId);
	}

	@Override
	protected List<Recipe> cursorToList(Cursor cursor) {
		
		List<Recipe> recipes = new ArrayList<Recipe>();
		if(cursor.getCount() > 0){
			
			while (cursor.moveToNext()) {
				Recipe recipe = new Recipe();
				recipe.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_ID)));
				recipe.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_NAME)));
				recipe.setImage(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_IMAGE)));
				recipe.setDetails(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_DETAILS)));
				recipe.setCategoryId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_CATEGORY_ID)));
				
				recipes.add(recipe);
			}
		}
		return recipes;

	}
}
