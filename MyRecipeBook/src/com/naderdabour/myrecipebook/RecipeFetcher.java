package com.naderdabour.myrecipebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.naderdabour.myrecipebook.viewmodels.CategoryVM;
import com.naderdabour.myrecipebook.viewmodels.IngredientVM;
import com.naderdabour.myrecipebook.viewmodels.MeasurementVM;
import com.naderdabour.myrecipebook.viewmodels.ProductVM;
import com.naderdabour.myrecipebook.viewmodels.RecipeFullVM;
import com.naderdabour.myrecipebook.viewmodels.RecipeSimpleVM;

import android.app.AlertDialog;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecipeFetcher {

	private static final String RECIPES_URL = "http://myrecipebook.apphb.com/api/recipes/";
	private static final String LOG = "RecipeFetcher";
	private static final String RECIPE_ID = "id";
	private static final String RECIPE_NAME = "name";
	private static final String RECIPE_CATEGORY = "categoryId";
	private static final String RECIPE_DETAILS = "details";
	private static final String RECIPE_INGREDIENTS = "ingredients";
	private static final String INGREDIENT_MEASUREMENT = "measurement";
	private static final String INGREDIENT_PRODUCT = "product";
	private static final String INGREDIENT_QUANTITY = "quantity";

	private Context context;

	private List<RecipeSimpleVM> recipes;
	private RecipeFullVM selectedRecipe;
	private Intent openSelectedRecipeIntent;
	private boolean isArrayResponce;
	private boolean recipeResponseOK;

	public RecipeFetcher(Context context){
		this.context = context;
	}

	public void getRemote(){

		if(!(context instanceof ListActivity)){
			return;
		}

		this.isArrayResponce = true;
		this.recipeResponseOK = true;
		this.recipes = new ArrayList<RecipeSimpleVM>();

		new RecipesFetcherAsync().execute(RECIPES_URL);
	}

	public void getRemote(long id, Intent intent){

		if(!(context instanceof ListActivity)){
			return;
		}

		this.isArrayResponce = false;
		this.recipeResponseOK = true;
		this.openSelectedRecipeIntent = intent;

		new RecipesFetcherAsync().execute(RECIPES_URL + Long.toString(id));
	}

	private class RecipesFetcherAsync extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... args) {

			String urlAddress = args[0];

			String result = getHttpInformation(urlAddress);

			if(result != null){

				parseResultInformation(result);

			} else {

				recipeResponseOK = false;
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {

			if(!recipeResponseOK){

				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle(R.string.get_remote_error);
				alert.setMessage(R.string.cannot_get_recipes);
				alert.setPositiveButton(R.string.ok, null);
				alert.create();
				AlertDialog theAlertDialog = alert.create();
				theAlertDialog.show();

			} else if(isArrayResponce){

				ListView listView = ((ListActivity)context).getListView();

				ArrayAdapter<RecipeSimpleVM> adapter = new RecipeListAdapter(context, recipes);

				listView.setAdapter(adapter);

			} else {
				openSelectedRecipeIntent.putExtra(RecipesActivity.RECIPE_VIEW_MODEL, selectedRecipe);

				((Activity)context).startActivityForResult(
						openSelectedRecipeIntent, RecipesActivity.EDIT_REMOTE_RECIPE_ACTIVITY);
			}
		}

		private String getHttpInformation(String urlAddress) {

			DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());

			HttpGet httpGet = new HttpGet(urlAddress);

			httpGet.setHeader("Content-type", "application/json");

			InputStream inputStream = null;

			String result = null;

			try{

				HttpResponse response = httpClient.execute(httpGet);

				if(response.getStatusLine().getStatusCode() == 200){

					HttpEntity entity = response.getEntity();

					inputStream = entity.getContent();

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream, "UTF-8"), 8);

					result = readInformation(reader);

				} else {

					recipeResponseOK = false;
				}
			}
			catch (IOException e) {
				
				recipeResponseOK = false;
				Log.v(LOG, e.toString());
			}
			finally{
				try{
					if(inputStream!= null) {
						
						inputStream.close();
					}
				}catch (IOException e) {
					
					recipeResponseOK = false;
					Log.v(LOG, e.toString());
				}
			}

			return result;
		}

		private String readInformation(BufferedReader reader) throws IOException {

			StringBuilder sb = new StringBuilder();

			String line = null;

			while((line = reader.readLine()) != null){

				sb.append(line + "\n");
			}
			return sb.toString();
		}


		private void parseResultInformation(String result) {

			if(isArrayResponce){
				parseRecipeList(result);
			} else {
				parseSingleRecipe(result);
			}
		}

		private void parseSingleRecipe(String result) {

			try {
				JSONObject jsonData = new JSONObject(result);
				selectedRecipe = getFullRecipeFromJson(jsonData);

			} catch (JSONException e) {
				recipeResponseOK = false;
				Log.v(LOG, e.toString());
			}
		}

		private void parseRecipeList(String result) {

			try {
				JSONArray jsonData = new JSONArray(result);

				for (int i = 0; i < jsonData.length(); i++) {

					RecipeSimpleVM recipeSimple = 
							getSimpleRecipeFromJson(jsonData.getJSONObject(i));

					recipes.add(recipeSimple);
				}

			} catch (JSONException e) {
				recipeResponseOK = false;
				Log.v(LOG, e.toString());
			}
		}

		private RecipeSimpleVM getSimpleRecipeFromJson(JSONObject jsonObject) throws JSONException {

			int id = jsonObject.getInt(RECIPE_ID);
			String name = jsonObject.getString(RECIPE_NAME);
			int categoryId = jsonObject.getInt(RECIPE_CATEGORY);
			String image = RecipesActivity.CATEGORY_IMAGE + categoryId;
			CategoryVM category = new CategoryVM();
			category.setId(categoryId);
			RecipeSimpleVM recipeToReturn = new RecipeSimpleVM(id, name,category, image);
			return recipeToReturn;
		}

		private RecipeFullVM getFullRecipeFromJson(JSONObject jsonObject) throws JSONException {

			int id = jsonObject.getInt(RECIPE_ID);
			String name = jsonObject.getString(RECIPE_NAME);
			int categoryId = jsonObject.getInt(RECIPE_CATEGORY);
			String details = jsonObject.getString(RECIPE_DETAILS);

			List<IngredientVM> ingredients = new ArrayList<IngredientVM>();

			JSONArray jsonArray = jsonObject.getJSONArray(RECIPE_INGREDIENTS);

			for (int i = 0; i < jsonArray.length(); i++) {

				IngredientVM ingredientVM = getIngredientsFromJson(jsonArray.getJSONObject(i));
				ingredients.add(ingredientVM);
			}

			CategoryVM categoryVM = new CategoryVM();
			categoryVM.setId(categoryId);

			RecipeFullVM recipeToReturn = new RecipeFullVM();
			recipeToReturn.setId(id);
			recipeToReturn.setName(name);
			recipeToReturn.setCategory(categoryVM);
			recipeToReturn.setDetails(details);
			recipeToReturn.setIngredients(ingredients);

			return recipeToReturn;
		}

		private IngredientVM getIngredientsFromJson(JSONObject jsonObject) throws JSONException{

			String product = jsonObject.getString(INGREDIENT_PRODUCT);
			String measurement = jsonObject.getString(INGREDIENT_MEASUREMENT);
			double quantity = jsonObject.getDouble(INGREDIENT_QUANTITY);

			MeasurementVM measurementVM = new MeasurementVM();
			measurementVM.setName(measurement);
			
			ProductVM productVM = new ProductVM();
			productVM.setName(product);
			
			IngredientVM ingredientVM = new IngredientVM();
			ingredientVM.setMeasurement(measurementVM);
			ingredientVM.setProduct(productVM);
			ingredientVM.setQuantity(quantity);

			return ingredientVM;
		}

	}
}
