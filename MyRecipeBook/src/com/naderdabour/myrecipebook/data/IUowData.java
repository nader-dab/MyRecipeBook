package com.naderdabour.myrecipebook.data;

import com.naderdabour.myrecipebook.models.*;

// Specifies the required
// methods for a central Datasource object
public interface IUowData {

	IDatasource<Category> getCategories();
	IDatasource<Product> getProducts();
	IDatasource<Measurement> getMeasurements();
	IDatasource<Ingredient> getIngredients();
	IDatasource<Recipe> getRecipes();

}
