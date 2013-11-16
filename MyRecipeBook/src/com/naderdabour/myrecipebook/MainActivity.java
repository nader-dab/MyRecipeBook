package com.naderdabour.myrecipebook;

import java.util.ArrayList;
import java.util.List;

import com.naderdabour.myrecipebook.data.DatabaseHelper;
import com.naderdabour.myrecipebook.data.IReadable;
import com.naderdabour.myrecipebook.data.IUowData;
import com.naderdabour.myrecipebook.data.UowData;
import com.naderdabour.myrecipebook.models.*;
import com.naderdabour.myrecipebook.utils.DbInitializer;
import com.naderdabour.myrecipebook.utils.DbModelHelper;
import com.naderdabour.myrecipebook.utils.ViewModelHelper;
import com.naderdabour.myrecipebook.viewmodels.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

	public static final String RECIPE_VIEW_MODEL = ".viewmodels.RecipeFullVM";
	public static final String CATEGORY_VIEW_MODEL = ".viewmodels.CategoryVM";
	public static final String MEASUREMENT_VIEW_MODEL = ".viewmodels.MeasurementVM";

	public static final String NEW_RECIPE = "com.naderdabour.myrecipebook.NEW_RECIPE";
	public static final String EDIT_RECIPE = "com.naderdabour.myrecipebook.EDIT_RECIPE";

	private static final int DELETE_RECIPE_MENU_ID = 101;
	private static final int ADD_RECIPE_ACTIVITY = 102;
	private static final int EDIT_RECIPE_ACTIVITY = 103;

	private IUowData uowData;
	private ViewModelHelper vmHelper;
	private List<Recipe> recipes;
	private int currentItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		uowData = new UowData(this);
		((IReadable) uowData).open();

		DbInitializer.init(uowData); 

		recipes = uowData.getRecipes().findAll();

		vmHelper = new ViewModelHelper(uowData);

		this.registerForContextMenu(getListView());

		refreshDisplay();
	}


	private void refreshDisplay() {

		List<RecipeSimpleVM> recipesVM = vmHelper.getRecipeSimpleVM(recipes);

		ArrayAdapter<RecipeSimpleVM> adapter = new RecipeListAdapter(this, recipesVM);

		setListAdapter(adapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

		currentItem = (int)info.id;

		menu.add(0, DELETE_RECIPE_MENU_ID, 0, R.string.recipe_delete);
		menu.add(0, 0, 0, R.string.cancel);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case DELETE_RECIPE_MENU_ID: deleteRecipe();
		break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	private void deleteRecipe(long recipeId) {

		List<Ingredient> ingredients = uowData.getIngredients()
				.findFiltered(DatabaseHelper.TABLE_INGREDIENT_RECIPE_ID +"=" + recipeId, null);

		for (Ingredient ingredient : ingredients) {

			uowData.getIngredients().remove(ingredient.getId());
			uowData.getProducts().remove(ingredient.getProductId());
		}

		uowData.getRecipes().remove(recipeId);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.add_recipe: addRecipe();
		break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void deleteRecipe(){

		long recipeId = recipes.get(currentItem).getId();
		deleteRecipe(recipeId);
		recipes = uowData.getRecipes().findAll();
		refreshDisplay();
		
	}
	private void addRecipe() {

		Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
		
		RecipeFullVM recipeToCreate = new RecipeFullVM();

		List<Category> categories = uowData.getCategories().findAll();
		List<Measurement> measurements = uowData.getMeasurements().findAll();
		
		ArrayList<CategoryVM> categoriesToDisplay = 
				(ArrayList<CategoryVM>) vmHelper.getCategoryVM(categories);
		
		ArrayList<MeasurementVM> measurementsToDisplay = 
				(ArrayList<MeasurementVM>) vmHelper.getMeasurementVM(measurements);
		
		intent.putExtra(RECIPE_VIEW_MODEL, recipeToCreate);
		intent.putParcelableArrayListExtra(CATEGORY_VIEW_MODEL, categoriesToDisplay);
		
		
		intent.putParcelableArrayListExtra(MEASUREMENT_VIEW_MODEL, measurementsToDisplay);
		
		startActivityForResult(intent, ADD_RECIPE_ACTIVITY);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == ADD_RECIPE_ACTIVITY && resultCode == RESULT_OK){

			RecipeFullVM recipeToAdd = data.getParcelableExtra(RECIPE_VIEW_MODEL);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (uowData instanceof IReadable) {
			((IReadable) uowData).open();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (uowData instanceof IReadable) {
			((IReadable) uowData).close();
		}
	}
}
