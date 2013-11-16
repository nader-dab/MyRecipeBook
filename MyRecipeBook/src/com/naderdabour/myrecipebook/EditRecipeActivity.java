package com.naderdabour.myrecipebook;

import java.util.ArrayList;
import java.util.List;

import com.naderdabour.myrecipebook.viewmodels.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
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
	private Button ingredientAddButton;
	private TableLayout ingredientsScrollViewTableLayout; 
	
	private View currentItem;
	private boolean hasChange;
	private boolean isEditing;
	private boolean isCreating;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		initComponents();
		
		Intent intent = getIntent();
		
		recipe = intent.getParcelableExtra(MainActivity.RECIPE_VIEW_MODEL);
		categories = intent.getParcelableArrayListExtra(MainActivity.CATEGORY_VIEW_MODEL);
		measurements = intent.getParcelableArrayListExtra(MainActivity.MEASUREMENT_VIEW_MODEL);
		categorySpinner.setAdapter(
				new ArrayAdapter<CategoryVM>(this, android.R.layout.simple_list_item_1, categories));
		
		isEditing = intent.getBooleanExtra(MainActivity.EDIT_RECIPE, false);
		isCreating = intent.getBooleanExtra(MainActivity.NEW_RECIPE, false);
		
		saveRecipeButton.setOnClickListener(saveRecipeButtonListener);
		ingredientAddButton.setOnClickListener(ingredientAddButtonListener);
		
		if(isEditing){
			handleEditing();
		} else if(isCreating){
			handleCreating();
		}
	}
	private TextWatcher textChangedListener = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			hasChange = true;
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnClickListener ingredientAddButtonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			inflateEmptyIngredient();
			
		}
	};
	
	private OnClickListener saveRecipeButtonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			if(titleMissing()){
				return;
			}
			
			recipe.setName(recipeTitleEditText.getText().toString());
			recipe.setDetails(recipeDetailsEditText.getText().toString());
			recipe.setCategory(categories.get(categorySpinner.getSelectedItemPosition()));
			recipe.setImage(MainActivity.CATEGORY_IMAGE + recipe.getCategory().getId());
			
			List<IngredientVM> ingredients = getIngredientsFromTableLayout();
			
			recipe.setIngredients(ingredients);
			
			Intent intent = new Intent();
			
			intent.putExtra(MainActivity.RECIPE_VIEW_MODEL, recipe);
			setResult(RESULT_OK, intent);
			finish();
		}

		private List<IngredientVM> getIngredientsFromTableLayout() {
			
			List<IngredientVM> ingredients = new ArrayList<IngredientVM>();
			
			for (int i = 0; i < ingredientsScrollViewTableLayout.getChildCount(); i++) {
				
				View ingredientView = ingredientsScrollViewTableLayout.getChildAt(i);
				
				EditText ingredientProductEditText = (EditText) ingredientView.findViewById(R.id.ingredientProductEditText);
				EditText ingredientQuantityEditText = (EditText) ingredientView.findViewById(R.id.ingredientQuantityEditText);
				Spinner ingredientMeasurementSpinner = (Spinner) ingredientView.findViewById(R.id.ingredientMeasurementSpinner);
				
				boolean productIsEmpty = ingredientProductEditText.getText().length() == 0;
				boolean ingredientIsEmpty = ingredientQuantityEditText.getText().length() == 0;
				
				if(productIsEmpty || ingredientIsEmpty){
					continue;
				}
				
				ProductVM productVM = new ProductVM();
				productVM.setName(ingredientProductEditText.getText().toString());
				
				IngredientVM ingredientVM = new IngredientVM();
				ingredientVM.setProduct(productVM);
				ingredientVM.setMeasurement(measurements.get(ingredientMeasurementSpinner.getSelectedItemPosition()));
				ingredientVM.setQuantity(Double.parseDouble(ingredientQuantityEditText.getText().toString()));
				ingredients.add(ingredientVM);
			}
			
			return ingredients;
		}		
	};

	private boolean titleMissing() {
		boolean titleMissing = recipeTitleEditText.getText().length() == 0;
		if(recipeTitleEditText.getText().length() == 0){
			AlertDialog.Builder alert = new AlertDialog.Builder(EditRecipeActivity.this);
			alert.setTitle(R.string.invalid_recipe);
			alert.setMessage(R.string.invalid_recipe_title);
			alert.setPositiveButton(R.string.ok, null);
			alert.create();
			AlertDialog theAlertDialog = alert.create();
			theAlertDialog.show();
		}
		
		return titleMissing;
	}
	
	private void handleCreating() {
		setTitle(R.string.recipe_new_title);
		recipeTitleEditText.requestFocus();
		saveRecipeButton.setText(R.string.recipe_create_button);
		
	}

	private void handleEditing() {
		
		setTitle(R.string.recipe_edit_title);
		recipeTitleEditText.setText(recipe.getName());
		categorySpinner.setSelection((int)(recipe.getCategory().getId() - 1));
		recipeDetailsEditText.setText(recipe.getDetails());
		saveRecipeButton.setText(R.string.recipe_edit_button);
		for (IngredientVM ingredient : recipe.getIngredients()) {
			inflateExistingIngredient(ingredient);
		}
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(recipeTitleEditText.getWindowToken(), 0);
		
	}

	private void initComponents(){
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
		recipeTitleEditText = (EditText) findViewById(R.id.recipeTitleEditText);
		recipeDetailsEditText = (EditText) findViewById(R.id.recipeDetailsEditText);
		saveRecipeButton = (Button) findViewById(R.id.saveRecipeButton);
		ingredientAddButton = (Button) findViewById(R.id.ingredientAddButton);
		ingredientsScrollViewTableLayout = (TableLayout) findViewById(R.id.ingredientsScrollViewTableLayout);
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
		
		recipeTitleEditText.addTextChangedListener(textChangedListener);
		recipeDetailsEditText.addTextChangedListener(textChangedListener);
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
			inflateEmptyIngredient();
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
    }
    
    private void inflateExistingIngredient(IngredientVM ingredient){
    	
    	LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    	
		View ingredientRow = inflater.inflate(R.layout.ingredient_list_entry, null);
		
		Spinner measurementSpinner = (Spinner) ingredientRow.findViewById(R.id.ingredientMeasurementSpinner);
		EditText productEditText = (EditText) ingredientRow.findViewById(R.id.ingredientProductEditText);
		EditText quantityEditText = (EditText) ingredientRow.findViewById(R.id.ingredientQuantityEditText);

		measurementSpinner.setAdapter(
				new ArrayAdapter<MeasurementVM>(this, android.R.layout.simple_list_item_1, measurements));
		
		measurementSpinner.setSelection((int)(ingredient.getMeasurement().getId() - 1));
		
		productEditText.setText(ingredient.getProduct().getName());
		productEditText.addTextChangedListener(textChangedListener);
		quantityEditText.setText(Double.toString(ingredient.getQuantity()));
		quantityEditText.addTextChangedListener(textChangedListener);
		
		registerForContextMenu(ingredientRow);
		ingredientsScrollViewTableLayout.addView(ingredientRow);
    	
    }
    
    private void inflateEmptyIngredient() {
    	
    	LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    	
		View ingredientRow = inflater.inflate(R.layout.ingredient_list_entry, null);
		
		Spinner measurementSpinner = (Spinner) ingredientRow.findViewById(R.id.ingredientMeasurementSpinner);
		EditText productEditText = (EditText) ingredientRow.findViewById(R.id.ingredientProductEditText);
		EditText quantityEditText = (EditText) ingredientRow.findViewById(R.id.ingredientQuantityEditText);
		
		quantityEditText.requestFocus();
		
		productEditText.addTextChangedListener(textChangedListener);
		quantityEditText.addTextChangedListener(textChangedListener);
		
		measurementSpinner.setAdapter(
				new ArrayAdapter<MeasurementVM>(this, android.R.layout.simple_list_item_1, measurements));
		
		registerForContextMenu(ingredientRow);
		ingredientsScrollViewTableLayout.addView(ingredientRow);
	}
    
   
	@Override
	public void onBackPressed() {
		cancelActivity();
	}
	
	private void cancelActivity(){
		Intent intent = new Intent();
		setResult(RESULT_CANCELED, intent);
		finish();
	}
}
