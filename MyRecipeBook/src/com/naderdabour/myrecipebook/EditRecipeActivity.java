package com.naderdabour.myrecipebook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.naderdabour.myrecipebook.utils.ImageHelper;
import com.naderdabour.myrecipebook.viewmodels.CategoryVM;
import com.naderdabour.myrecipebook.viewmodels.IngredientVM;
import com.naderdabour.myrecipebook.viewmodels.MeasurementVM;
import com.naderdabour.myrecipebook.viewmodels.ProductVM;
import com.naderdabour.myrecipebook.viewmodels.RecipeFullVM;

public class EditRecipeActivity extends Activity {

	private static final String USER_IMAGE = "recipes";
	private static final String IMAGE_FILE_EXTENSION = ".png";

	private static final int REMOVE_INGREDIENT = 101;
	private static final int TAKE_PICTURE = 102;
	private List<CategoryVM> categories;
	private List<MeasurementVM> measurements;
	private RecipeFullVM recipe;

	private Spinner categorySpinner;
	private EditText recipeTitleEditText;
	private EditText recipeDetailsEditText;
	private Button saveRecipeButton;
	private Button ingredientAddButton;
	private Button useDefaultImageButton;
	private Button takeCameraImageButton;
	private TableLayout ingredientsScrollViewTableLayout; 
	private ImageView recipeImageView;

	private View selectedIngredient;
	private boolean isInEditingMode;
	private boolean isInCreatingMode;
	private boolean isUsingCameraImage;
	private String cameraImagePath;
	private ImageHelper imageHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		imageHelper = new ImageHelper(this);
		initComponents();

		Intent intent = getIntent();

		recipe = intent.getParcelableExtra(RecipesActivity.RECIPE_VIEW_MODEL);
		categories = intent.getParcelableArrayListExtra(RecipesActivity.CATEGORY_VIEW_MODEL);
		measurements = intent.getParcelableArrayListExtra(RecipesActivity.MEASUREMENT_VIEW_MODEL);
		categorySpinner.setAdapter(
				new ArrayAdapter<CategoryVM>(this, android.R.layout.simple_list_item_1, categories));

		isInEditingMode = intent.getBooleanExtra(RecipesActivity.EDIT_RECIPE, false);
		isInCreatingMode = intent.getBooleanExtra(RecipesActivity.NEW_RECIPE, false);


		if(isInEditingMode){
			handleEditing();
		} else if(isInCreatingMode){
			handleCreating();
		}
	}
	
	private void initComponents(){
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
		recipeTitleEditText = (EditText) findViewById(R.id.recipeTitleEditText);
		recipeDetailsEditText = (EditText) findViewById(R.id.recipeDetailsEditText);
		saveRecipeButton = (Button) findViewById(R.id.saveRecipeButton);
		ingredientAddButton = (Button) findViewById(R.id.ingredientAddButton);
		useDefaultImageButton = (Button) findViewById(R.id.useDefaultImageButton);
		takeCameraImageButton = (Button) findViewById(R.id.takeCameraImageButton);
		ingredientsScrollViewTableLayout = (TableLayout) findViewById(R.id.ingredientsScrollViewTableLayout);
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
		recipeImageView = (ImageView) findViewById(R.id.recipeImageView);

		saveRecipeButton.setOnClickListener(saveRecipeButtonListener);
		ingredientAddButton.setOnClickListener(ingredientAddButtonListener);
		useDefaultImageButton.setOnClickListener(useDefaultImageButtonListener);
		takeCameraImageButton.setOnClickListener(takeCameraImageButtonListener);
		categorySpinner.setOnItemSelectedListener(categorySpinnerListener);
	}


	private OnClickListener ingredientAddButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			inflateEmptyIngredient();

		}
	};

	private OnClickListener saveRecipeButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			saveRecipe();
		}
	};

	private void saveRecipe() {
		
		if(titleMissing()){
			return;
		}

		recipe.setName(recipeTitleEditText.getText().toString());
		recipe.setDetails(recipeDetailsEditText.getText().toString());
		recipe.setCategory(categories.get(categorySpinner.getSelectedItemPosition()));

		if(isUsingCameraImage){
			recipe.setImage(cameraImagePath);
		} else {

			recipe.setImage(RecipesActivity.CATEGORY_IMAGE + recipe.getCategory().getId());
		}

		List<IngredientVM> ingredients = getIngredientsFromTableLayout();

		recipe.setIngredients(ingredients);

		Intent intent = new Intent();

		intent.putExtra(RecipesActivity.RECIPE_VIEW_MODEL, recipe);
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
		isUsingCameraImage = false;
		recipeTitleEditText.requestFocus();
		saveRecipeButton.setText(R.string.recipe_create_button);

	}

	private void handleEditing() {

		setTitle(R.string.recipe_edit_title);
		recipeTitleEditText.setText(recipe.getName());
		categorySpinner.setSelection((int)(recipe.getCategory().getId() - 1));
		recipeDetailsEditText.setText(recipe.getDetails());
		saveRecipeButton.setText(R.string.recipe_edit_button);

		if(recipe.getName().startsWith(RecipesActivity.CATEGORY_IMAGE)){
			setDefaultImageResource();
		} else {
			isUsingCameraImage = true;
			cameraImagePath = recipe.getImage();

			try {
				recipeImageView.setImageBitmap(imageHelper.readFile(cameraImagePath));

			} catch (IOException e) {
				setDefaultImageResource();
			} catch (NullPointerException e){
				setDefaultImageResource();
			}
		}


		for (IngredientVM ingredient : recipe.getIngredients()) {
			inflateExistingIngredient(ingredient);
		}
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}


	private OnItemSelectedListener categorySpinnerListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if(!isUsingCameraImage){
				setDefaultImageResource();
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};

	private OnClickListener takeCameraImageButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, TAKE_PICTURE);
		}
	};

	private void setDefaultImageResource(){
		isUsingCameraImage = false;
		cameraImagePath = RecipesActivity.CATEGORY_IMAGE + (categorySpinner.getSelectedItemPosition() + 1);
		int imageResource = getResources().getIdentifier(cameraImagePath, "drawable", getPackageName());
		if(imageResource != 0){
			recipeImageView.setImageResource(imageResource);
		}
	}

	private OnClickListener useDefaultImageButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			setDefaultImageResource();
		}
	};

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		selectedIngredient = v;

		menu.add(0, REMOVE_INGREDIENT, 0, R.string.ingredient_remove);
		menu.add(0, 0, 0, R.string.cancel);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case REMOVE_INGREDIENT:
			ingredientsScrollViewTableLayout.removeView(selectedIngredient);
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
		case R.id.save_button:
			saveRecipe();
			break;
		case android.R.id.home:
			cancelActivity();
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

		quantityEditText.setText(Double.toString(ingredient.getQuantity()));

		registerForContextMenu(ingredientRow);
		ingredientsScrollViewTableLayout.addView(ingredientRow);

	}

	private void inflateEmptyIngredient() {

		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		View ingredientRow = inflater.inflate(R.layout.ingredient_list_entry, null);

		Spinner measurementSpinner = (Spinner) ingredientRow.findViewById(R.id.ingredientMeasurementSpinner);
		EditText quantityEditText = (EditText) ingredientRow.findViewById(R.id.ingredientQuantityEditText);

		quantityEditText.requestFocus();

		measurementSpinner.setAdapter(
				new ArrayAdapter<MeasurementVM>(this, android.R.layout.simple_list_item_1, measurements));

		registerForContextMenu(ingredientRow);
		ingredientsScrollViewTableLayout.addView(ingredientRow);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {

			isUsingCameraImage = true;
			Bundle extras = data.getExtras();
			Bitmap picture = (Bitmap) extras.get("data"); 
			recipeImageView.setImageBitmap(picture);

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();

			try {
				String newImageName = USER_IMAGE + UUID.randomUUID() + IMAGE_FILE_EXTENSION;
				cameraImagePath = imageHelper.createFile(newImageName, byteArray);

			} catch (IOException e) {

				setDefaultImageResource();
			}
		}
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
