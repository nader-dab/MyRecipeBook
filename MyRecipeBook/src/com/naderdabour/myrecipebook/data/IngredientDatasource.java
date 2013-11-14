package com.naderdabour.myrecipebook.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.naderdabour.myrecipebook.models.Category;
import com.naderdabour.myrecipebook.models.Ingredient;
import com.naderdabour.myrecipebook.models.Measurement;
import com.naderdabour.myrecipebook.models.Product;
import com.naderdabour.myrecipebook.models.Recipe;

public class IngredientDatasource extends GenericDatasource<Ingredient> {

	private static final String[] allColumns = {
		DatabaseHelper.TABLE_INGREDIENT_ID,
		DatabaseHelper.TABLE_INGREDIENT_MEASUREMENT_ID,
		DatabaseHelper.TABLE_INGREDIENT_PRODUCT_ID,
		DatabaseHelper.TABLE_INGREDIENT_QUANTITY,
		DatabaseHelper.TABLE_INGREDIENT_RECIPE_ID
	};
	
	private Context context;
	public IngredientDatasource(Context context) {
		super(context, DatabaseHelper.TABLE_INGREDIENT, allColumns);
		this.context = context;
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
				Ingredient ingredient = new Ingredient();
				ingredient.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_ID)));
				ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_QUANTITY)));
				ingredient.setMeasurementId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_MEASUREMENT_ID)));
				ingredient.setProductId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_PRODUCT_ID)));
				ingredient.setRecipeId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_INGREDIENT_RECIPE_ID)));
				ingredients.add(ingredient);
			}
		}
		return ingredients;
	}
}
