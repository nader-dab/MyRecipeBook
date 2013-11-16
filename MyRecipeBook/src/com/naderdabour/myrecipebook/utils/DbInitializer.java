package com.naderdabour.myrecipebook.utils;

import com.naderdabour.myrecipebook.data.IUowData;
import com.naderdabour.myrecipebook.models.Category;
import com.naderdabour.myrecipebook.models.Ingredient;
import com.naderdabour.myrecipebook.models.Measurement;
import com.naderdabour.myrecipebook.models.Product;
import com.naderdabour.myrecipebook.models.Recipe;

public class DbInitializer {

	private static final String[] categories = {
		"Main dish",
		"Desert",
		"Soup",
		"Side dish",
		"Snack",
		"Salad",
		"Dinner",
		"Beverage",
	};

	private static final String[] products = {
		"flour",
		"chicken",
		"eggs",
		"sea salt",
		"lemon",
		"honey mustard",
		"extra-virgin olive oil",
		"sweet potatoes",
	};

	private static final String[] measurements = {
		"gram(s)",
		"kilogram(s)",
		"mililiter(s)",
		"liter(s)",
		"cup(s)",
		"tablespoon(s)",
		"teaspoon(s)",
		"small",
		"medium",
		"large",
	};

	public static void init(IUowData uowData){

		boolean categoriesIsEmpty = uowData.getCategories().findAll().size() == 0;
		boolean productsIsEmpty = uowData.getProducts().findAll().size() == 0;
		boolean measurementsIsEmpty = uowData.getMeasurements().findAll().size() == 0;
		boolean recipesIsEmpty = uowData.getRecipes().findAll().size() == 0;
		boolean ingredientsIsEmpty = uowData.getIngredients().findAll().size() == 0;

		boolean dbIsEmpty = categoriesIsEmpty 
				&& productsIsEmpty 
				&& measurementsIsEmpty 
				&& recipesIsEmpty
				&& ingredientsIsEmpty;

		if(dbIsEmpty) {

			initCategories(uowData);
			initProducts(uowData);
			initMeasurements(uowData);
			initRecipes(uowData);
			initIngredients(uowData);
		}
	}

	private static void initCategories(IUowData uowData) {

		for (String cat : categories) {
			Category category= new Category();
			category.setName(cat);
			uowData.getCategories().create(category);
		}
	}

	private static void initProducts(IUowData uowData) {

		for (String prod : products) {
			Product product = new Product();
			product.setName(prod);
			uowData.getProducts().create(product);
		}
	}

	private static void initMeasurements(IUowData uowData) {

		for (String mes : measurements) {
			Measurement measurement = new Measurement();
			measurement.setName(mes);
			uowData.getMeasurements().create(measurement);
		}
	}

	private static void initRecipes(IUowData uowData){

		Recipe recipe;

		recipe = new Recipe();
		recipe.setCategoryId(1);
		recipe.setName("Chicken with sweet potatoes");
		recipe.setImage("image1");
		recipe.setDetails("Everything is put in an oven for 25 minutes. Very tasty.");
		uowData.getRecipes().create(recipe);

		recipe = new Recipe();
		recipe.setCategoryId(4);
		recipe.setName("Egg omllet");
		recipe.setImage("image4");
		recipe.setDetails("Fry egss in a pan for 5-10 minutes.");
		uowData.getRecipes().create(recipe);
	}

	private static void initIngredients(IUowData uowData) {

		Ingredient ingredient;

		ingredient = new Ingredient();
		ingredient.setMeasurementId(5);
		ingredient.setProductId(1);
		ingredient.setQuantity(1);
		ingredient.setRecipeId(1);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(6);
		ingredient.setProductId(5);
		ingredient.setQuantity(2);
		ingredient.setRecipeId(1);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(1);
		ingredient.setProductId(6);
		ingredient.setQuantity(3);
		ingredient.setRecipeId(1);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(5);
		ingredient.setProductId(6);
		ingredient.setQuantity(1);
		ingredient.setRecipeId(1);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(1);
		ingredient.setProductId(2);
		ingredient.setQuantity(500);
		ingredient.setRecipeId(1);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(9);
		ingredient.setProductId(3);
		ingredient.setQuantity(3);
		ingredient.setRecipeId(2);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(6);
		ingredient.setProductId(7);
		ingredient.setQuantity(2);
		ingredient.setRecipeId(2);
		uowData.getIngredients().create(ingredient);
	}
}
