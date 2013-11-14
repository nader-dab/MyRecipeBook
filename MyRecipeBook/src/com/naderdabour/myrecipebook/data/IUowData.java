package com.naderdabour.myrecipebook.data;

import com.naderdabour.myrecipebook.models.*;

public interface IUowData {
	
	void open();
	void close();
	
	IDatasource<Category> getCategories();
	IDatasource<Product> getProducts();
	IDatasource<Measurement> getMeasurements();
	IDatasource<Ingredient> getIngredients();
	IDatasource<Recipe> getRecipes();

}
