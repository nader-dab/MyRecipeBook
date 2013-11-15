package com.naderdabour.myrecipebook.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.naderdabour.myrecipebook.models.Ingredient;

public class IngredientDatasource extends GenericDatasource<Ingredient> {

	private static final String[] allColumns = {
		DatabaseHelper.TABLE_INGREDIENT_ID,
		DatabaseHelper.TABLE_INGREDIENT_MEASUREMENT_ID,
		DatabaseHelper.TABLE_INGREDIENT_PRODUCT_ID,
		DatabaseHelper.TABLE_INGREDIENT_QUANTITY,
		DatabaseHelper.TABLE_INGREDIENT_RECIPE_ID
	};
	
	public IngredientDatasource(Context context) {
		super(context, DatabaseHelper.TABLE_INGREDIENT, allColumns);
	}

	@Override
	protected ContentValues entryToContentValues(Ingredient entry) {
		
		ContentValues values = new ContentValues();
		
		values.put(DatabaseHelper.TABLE_INGREDIENT_QUANTITY, entry.getQuantity());
		values.put(DatabaseHelper.TABLE_INGREDIENT_MEASUREMENT_ID, entry.getMeasurementId());
		values.put(DatabaseHelper.TABLE_INGREDIENT_PRODUCT_ID, entry.getProductId());
		values.put(DatabaseHelper.TABLE_INGREDIENT_RECIPE_ID, entry.getRecipeId());
		return values;
	}

	@Override
	protected long getEntryId(Ingredient entry) {
		return entry.getId();
	}

	
	@Override
	protected void setEntryId(Ingredient entry, long insertId) {
		
		entry.setId(insertId);
	}

	@Override
	protected List<Ingredient> cursorToList(Cursor cursor) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		if(cursor.getCount() > 0){
			
			while (cursor.moveToNext()) {
				
				long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_ID));
				double quantity = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_QUANTITY));
				long measurementId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_MEASUREMENT_ID));
				long productId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_PRODUCT_ID));
				long recipeId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_RECIPE_ID));
				
				Ingredient ingredient = new Ingredient(id, quantity, measurementId, productId, recipeId);
				ingredients.add(ingredient);
			}
		}
		return ingredients;
	}
}
