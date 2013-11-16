package com.naderdabour.myrecipebook;

import java.util.List;

import com.naderdabour.myrecipebook.viewmodels.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;

public class EditRecipeActivity extends Activity {
	
	private static final int REMOVE_INGREDIENT = 101;
	private List<CategoryVM> categories;
	private List<MeasurementVM> measurements;
	private RecipeFullVM recipe;
	
	private Spinner categorySpinner;
	private EditText recipeTitleEditText;
	private EditText recipeDetailsEditText;
	private Button saveRecipeButton;
	private TableLayout ingredientsScrollViewTableLayout; 
	private View currentItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);

		initComponents();
		
		Intent intent = getIntent();
		
		recipe = intent.getParcelableExtra(MainActivity.RECIPE_VIEW_MODEL);
		categories = intent.getParcelableArrayListExtra(MainActivity.CATEGORY_VIEW_MODEL);
		measurements = intent.getParcelableArrayListExtra(MainActivity.MEASUREMENT_VIEW_MODEL);
		
		categorySpinner.setAdapter(
				new ArrayAdapter<CategoryVM>(this, android.R.layout.simple_list_item_1, categories));

		setTitle(R.string.recipe_new_title);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
	}

	private void initComponents(){
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
		recipeTitleEditText = (EditText) findViewById(R.id.recipeTitleEditText);
		recipeDetailsEditText = (EditText) findViewById(R.id.recipeDetailsEditText);
		saveRecipeButton = (Button) findViewById(R.id.saveRecipeButton);
		ingredientsScrollViewTableLayout = (TableLayout) findViewById(R.id.ingredientsScrollViewTableLayout);
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		currentItem = v;
		
		menu.add(0, REMOVE_INGREDIENT, 0, R.string.ingredient_remove);
		menu.add(0, 0, 0, R.string.cancel);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case REMOVE_INGREDIENT:
			ingredientsScrollViewTableLayout.removeView(currentItem);
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.edit_recipe, menu);

		return true;
	}
	
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch (item.getItemId()) {
    	
		case R.id.add_ingredient:
			inflateIngredientsScrollViewTableLayout();
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
    }
    
    private void inflateIngredientsScrollViewTableLayout() {
    	
    	LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    	
		View ingredientRow = inflater.inflate(R.layout.ingredient_list_entry, null);
		
		Spinner measurementSpinner = (Spinner) ingredientRow.findViewById(R.id.ingredientMeasurementSpinner);
		
		measurementSpinner.setAdapter(
				new ArrayAdapter<MeasurementVM>(this, android.R.layout.simple_list_item_1, measurements));
		
		ingredientsScrollViewTableLayout.addView(ingredientRow);
		
		registerForContextMenu(ingredientRow);
	}
}
