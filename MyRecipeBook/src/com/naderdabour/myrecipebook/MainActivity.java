package com.naderdabour.myrecipebook;

import java.util.ArrayList;
import java.util.List;

import com.naderdabour.myrecipebook.data.DatabaseHelper;
import com.naderdabour.myrecipebook.data.IReadable;
import com.naderdabour.myrecipebook.data.IUowData;
import com.naderdabour.myrecipebook.data.UowData;
import com.naderdabour.myrecipebook.models.*;
import com.naderdabour.myrecipebook.utils.DbInitializer;
import com.naderdabour.myrecipebook.utils.ViewModelHelper;
import com.naderdabour.myrecipebook.viewmodels.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	public static final String RECIPE_VIEW_MODEL = ".viewmodels.RecipeFullVM";
	public static final String CATEGORY_VIEW_MODEL = ".viewmodels.CategoryVM";
	public static final String MEASUREMENT_VIEW_MODEL = ".viewmodels.MeasurementVM";
	public static final String CATEGORY_IMAGE = "category_image";

	public static final String NEW_RECIPE = "com.naderdabour.myrecipebook.NEW_RECIPE";
	public static final String EDIT_RECIPE = "com.naderdabour.myrecipebook.EDIT_RECIPE";

	private static final int DELETE_RECIPE_MENU_ID = 101;
	private static final int ADD_RECIPE_ACTIVITY = 102;
	private static final int EDIT_RECIPE_ACTIVITY = 103;
	public static final int EDIT_REMOTE_RECIPE_ACTIVITY = 104;

	private IUowData uowData;
	private ViewModelHelper vmHelper;
	private List<Recipe> recipes;
	private int currentItem;
	private boolean isInMyRecipes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.registerForContextMenu(getListView());

		uowData = new UowData(this);
		((IReadable) uowData).open();

		DbInitializer.init(uowData); 

		vmHelper = new ViewModelHelper(uowData);

		refreshMyRecipes();

	}


	private void refreshMyRecipes() {
		
		isInMyRecipes = true;

		recipes = uowData.getRecipes().findFiltered(null, DatabaseHelper.TABLE_RECIPE_ID + " DESC");

		List<RecipeSimpleVM> recipesVM = vmHelper.getRecipeSimpleVM(recipes);

		ArrayAdapter<RecipeSimpleVM> adapter = new RecipeListAdapter(this, recipesVM);

		setListAdapter(adapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

		currentItem = (int)info.id;

		if(isInMyRecipes){
			menu.add(0, DELETE_RECIPE_MENU_ID, 0, R.string.recipe_delete);
			menu.add(0, 0, 0, R.string.cancel);
		}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.add_recipe: addNewRecipe();
		break;
		case R.id.my_recipe: refreshMyRecipes();
		break;
		case R.id.get_recipe: getRemoteRecipes();
		break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void getRemoteRecipes() {
		isInMyRecipes = false; 
		RecipeFetcher recipeFetcher = new RecipeFetcher(MainActivity.this);
		recipeFetcher.getRemote();
	}


	private void deleteRecipe(){
		
		long recipeId = recipes.get(currentItem).getId();
		vmHelper.deleteRecipe(recipeId);
		refreshMyRecipes();

	}

	private void addNewRecipe() {

		RecipeFullVM recipeToCreate = new RecipeFullVM();

		Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);

		putSpinnerData(intent);

		intent.putExtra(NEW_RECIPE, true);
		intent.putExtra(RECIPE_VIEW_MODEL, recipeToCreate);

		startActivityForResult(intent, ADD_RECIPE_ACTIVITY);
	}

	private void putSpinnerData(Intent intent) {

		List<Category> categories = uowData.getCategories().findAll();
		List<Measurement> measurements = uowData.getMeasurements().findAll();

		ArrayList<CategoryVM> categoriesToDisplay = 
				(ArrayList<CategoryVM>) vmHelper.getCategoryVM(categories);

		ArrayList<MeasurementVM> measurementsToDisplay = 
				(ArrayList<MeasurementVM>) vmHelper.getMeasurementVM(measurements);

		intent.putParcelableArrayListExtra(CATEGORY_VIEW_MODEL, categoriesToDisplay);
		intent.putParcelableArrayListExtra(MEASUREMENT_VIEW_MODEL, measurementsToDisplay);
	}

	@Override
	protected void onListItemClick(ListView l, View view, int position, long id) {
		super.onListItemClick(l, view, position, id);

		if(isInMyRecipes){
			
			RecipeFullVM recipe = vmHelper.getRecipeFullVM(recipes.get(position).getId());
			
			if(recipe != null){
				Intent intent = getEditIntent();
				intent.putExtra(RECIPE_VIEW_MODEL, recipe);
				startActivityForResult(intent, EDIT_RECIPE_ACTIVITY);
			}
		} else {
			
			RecipeFetcher recipeFetcher = new RecipeFetcher(MainActivity.this);
			TextView remoteRecipeId = (TextView) view.findViewById(R.id.recipeIdTextView);
			int remoteId = Integer.parseInt(remoteRecipeId.getText().toString());
			Intent intent = getEditIntent();
			recipeFetcher.getRemote(remoteId, intent);
		}
	}

	private Intent getEditIntent() {
		Intent intent = new Intent(this, EditRecipeActivity.class);	
		putSpinnerData(intent);
		intent.putExtra(EDIT_RECIPE, true);
		return intent;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		((IReadable) uowData).open();

		if(requestCode == ADD_RECIPE_ACTIVITY && resultCode == RESULT_OK){

			RecipeFullVM recipeToAdd = data.getParcelableExtra(RECIPE_VIEW_MODEL);

			vmHelper.addRecipeFullVM(recipeToAdd);
			refreshMyRecipes();

		} else if((requestCode == EDIT_RECIPE_ACTIVITY || requestCode == EDIT_REMOTE_RECIPE_ACTIVITY) && resultCode == RESULT_OK){

			RecipeFullVM recipeToUpdate = data.getParcelableExtra(RECIPE_VIEW_MODEL);
			vmHelper.deleteRecipe(recipeToUpdate.getId());
			vmHelper.addRecipeFullVM(recipeToUpdate);
			refreshMyRecipes();
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
