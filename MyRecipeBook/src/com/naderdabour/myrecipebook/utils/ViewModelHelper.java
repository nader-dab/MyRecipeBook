package com.naderdabour.myrecipebook.utils;

import java.util.ArrayList;
import java.util.List;

import com.naderdabour.myrecipebook.data.DatabaseHelper;
import com.naderdabour.myrecipebook.data.IUowData;
import com.naderdabour.myrecipebook.models.Category;
import com.naderdabour.myrecipebook.models.Ingredient;
import com.naderdabour.myrecipebook.models.Measurement;
import com.naderdabour.myrecipebook.models.Product;
import com.naderdabour.myrecipebook.models.Recipe;
import com.naderdabour.myrecipebook.viewmodels.CategoryVM;
import com.naderdabour.myrecipebook.viewmodels.IngredientVM;
import com.naderdabour.myrecipebook.viewmodels.MeasurementVM;
import com.naderdabour.myrecipebook.viewmodels.ProductVM;
import com.naderdabour.myrecipebook.viewmodels.RecipeFullVM;
import com.naderdabour.myrecipebook.viewmodels.RecipeSimpleVM;

public class ViewModelHelper {
	private IUowData uowData;
	
	public ViewModelHelper(IUowData uowData){
		this.uowData = uowData;
	}
	
	public RecipeSimpleVM getRecipeSimpleVM(Recipe recipe){
		
		long id = recipe.getId();
		String name = recipe.getName();
		String image = recipe.getImage();
		CategoryVM category = getCategoryVM(recipe.getCategoryId());
		
		RecipeSimpleVM recipeVm = new RecipeSimpleVM(id, name, category, image);
		return recipeVm;
		
	}
	public List<RecipeSimpleVM> getRecipeSimpleVM(List<Recipe> recipes){
		
		List<RecipeSimpleVM> vmListToReturn = new ArrayList<RecipeSimpleVM>();

		for (Recipe recipe : recipes) {

			RecipeSimpleVM recipeVm = getRecipeSimpleVM(recipe);
		
			vmListToReturn.add(recipeVm);
		}
		
		return vmListToReturn;
	}

	public RecipeSimpleVM getRecipeSimpleVM(long id){

		RecipeSimpleVM vmToReturn = null;

		Recipe recipe = uowData.getRecipes().findById(id);

		if(recipe != null){
			
			vmToReturn = getRecipeSimpleVM(recipe);
		}

		return vmToReturn;
	}

	public CategoryVM getCategoryVM(long id) {

		CategoryVM vmToReturn = null;

		Category dbCategory = uowData.getCategories().findById(id);

		if(dbCategory != null){

			vmToReturn = new CategoryVM(id, dbCategory.getName());
		}

		return vmToReturn;
	}
	public RecipeFullVM getRecipeFullVM(long id){

		RecipeFullVM vmToReturn = null;

		Recipe recipe = uowData.getRecipes().findById(id);

		if (recipe != null){

			CategoryVM categoryToDisplay = getCategoryVM(recipe.getCategoryId());

			List<Ingredient>ingredients = uowData.getIngredients()
					.findFiltered(DatabaseHelper.TABLE_INGREDIENT_RECIPE_ID + "=" + recipe.getId(), null);

			List<IngredientVM> ingredientsToDisplay = new ArrayList<IngredientVM>();

			for (Ingredient ingredient : ingredients) {

				MeasurementVM measurementToDisplay = getMeasurementVM(ingredient.getMeasurementId());

				ProductVM productToDisplay = getProductVM(ingredient.getProductId());

				IngredientVM ingredientToDisplay = 
						new IngredientVM(
								ingredient.getId(), 
								ingredient.getQuantity(), 
								measurementToDisplay, 
								productToDisplay);

				ingredientsToDisplay.add(ingredientToDisplay);
			}

			vmToReturn = new RecipeFullVM(
					recipe.getId(),
					recipe.getName(),
					categoryToDisplay,
					recipe.getImage(),
					ingredientsToDisplay,
					recipe.getDetails());
		}
		return vmToReturn;
	}

	public ProductVM getProductVM(long id) {

		ProductVM vmToReturn = null;

		Product product = uowData.getProducts().findById(id);

		if(product != null){
			vmToReturn = new ProductVM(id, product.getName());
		}

		return vmToReturn;
	}

	public MeasurementVM getMeasurementVM(long id) {

		MeasurementVM vmToReturn = null;

		Measurement measurement = uowData.getMeasurements().findById(id);

		if(measurement != null){
			vmToReturn = new MeasurementVM(id, measurement.getName());
		}
		return vmToReturn;
	}
}
