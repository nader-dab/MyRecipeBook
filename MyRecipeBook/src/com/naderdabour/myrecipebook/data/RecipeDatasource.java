package com.naderdabour.myrecipebook.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.naderdabour.myrecipebook.models.Category;
import com.naderdabour.myrecipebook.models.Ingredient;
import com.naderdabour.myrecipebook.models.Recipe;

public class RecipeDatasource extends GenericDatasource<Recipe> {

	private static final String[] allColumns = {
		DatabaseHelper.TABLE_RECIPE_ID,
		DatabaseHelper.TABLE_RECIPE_NAME,
		DatabaseHelper.TABLE_RECIPE_DETAILS,
		DatabaseHelper.TABLE_RECIPE_IMAGE,
		DatabaseHelper.TABLE_RECIPE_CATEGORY_ID
	};
	
	private Context context;
	
	public RecipeDatasource(Context context) {
		super(context, DatabaseHelper.TABLE_RECIPE, allColumns);
		this.context = context;
	}
	
	@Override
	protected ContentValues entryToContentValues(Recipe entry) {
		
		ContentValues values = new ContentValues();
		Log.v("RecipeDatasource","entryToContentValues begin");
		values.put(DatabaseHelper.TABLE_RECIPE_NAME, entry.getName());
		Log.v("RecipeDatasource","Name OK");
		values.put(DatabaseHelper.TABLE_RECIPE_DETAILS, entry.getDetails());
		Log.v("RecipeDatasource","Details OK");
		values.put(DatabaseHelper.TABLE_RECIPE_IMAGE, entry.getImage());
		Log.v("RecipeDatasource","Image OK");
		values.put(DatabaseHelper.TABLE_RECIPE_CATEGORY_ID, entry.getCategory().getId());
		Log.v("RecipeDatasource","Category.getId() OK");
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
				Log.v("RecipeDatasource","cursorToList begin");
				Recipe recipe = new Recipe();
				recipe.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_ID)));
				Log.v("RecipeDatasource","cursorToList setId");
				recipe.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_NAME)));
				Log.v("RecipeDatasource","cursorToList setName");
				recipe.setImage(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_IMAGE)));
				Log.v("RecipeDatasource","cursorToList setImage");
				recipe.setDetails(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_DETAILS)));
				Log.v("RecipeDatasource","cursorToList setDetails");
				
				CategoryDatasource categoryDatasource = new CategoryDatasource(context);
				Log.v("RecipeDatasource","cursorToList got categoryDatasource");
				
				long categoryId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_RECIPE_CATEGORY_ID));
				Log.v("RecipeDatasource","cursorToList got categoryId " + categoryId);
				boolean categoryDatasourceIsNull = categoryDatasource == null;
				Log.v("RecipeDatasource","cursorToList categoryDatasourceIsNull " + categoryDatasourceIsNull);
				
				Category category = categoryDatasource.findById(categoryId);
				
				Log.v("RecipeDatasource","cursorToList found category");
				recipe.setCategory(category);
				Log.v("RecipeDatasource","cursorToList setCategory");
				IngredientDatasource ingredientDatasource = new IngredientDatasource(context);
				Log.v("RecipeDatasource","cursorToList got ingredientDatasource");	
				String where = DatabaseHelper.TABLE_INGREDIENT_RECIPE_ID + "=" + recipe.getId();
				Log.v("RecipeDatasource","cursorToList where string: " + where);	
				List<Ingredient> ingredients = ingredientDatasource.findFiltered(where, null);
				Log.v("RecipeDatasource","cursorToList got ingredients list");	
				recipe.setIngredients(ingredients);
				Log.v("RecipeDatasource","cursorToList setIngredients");	
				recipes.add(recipe);
			}
		}
		return recipes;

	}

}
