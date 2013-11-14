package com.naderdabour.myrecipebook.data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.naderdabour.myrecipebook.models.Category;
import com.naderdabour.myrecipebook.models.Ingredient;
import com.naderdabour.myrecipebook.models.Measurement;
import com.naderdabour.myrecipebook.models.Product;
import com.naderdabour.myrecipebook.models.Recipe;

public class UowData implements IUowData {

	private IDatasource<Category> categoriesDatasource;
	private IDatasource<Product> productsDatasource;
	private IDatasource<Measurement> measurementDatasource;
	private IDatasource<Ingredient> ingredientsDatasource;
	private IDatasource<Recipe> recipesDatasource;
	private List<IDatasource> datasources;
	
	public UowData(Context context){
		categoriesDatasource = new CategoryDatasource(context); 
		productsDatasource = new ProductDatasource(context); 
		measurementDatasource = new MeasurementDatasource(context); 
		ingredientsDatasource = new IngredientDatasource(context); 
		recipesDatasource = new RecipeDatasource(context); 
		
		datasources = new ArrayList<IDatasource>();
		
		datasources.add(categoriesDatasource);
		datasources.add(productsDatasource);
		datasources.add(measurementDatasource);
		datasources.add(ingredientsDatasource);
		datasources.add(recipesDatasource);
	}
	
	@Override
	public IDatasource<Category> getCategories() {
		return categoriesDatasource;
	}

	@Override
	public IDatasource<Product> getProducts() {
		return productsDatasource;
	}

	@Override
	public IDatasource<Measurement> getMeasurements() {
		return measurementDatasource;
	}

	@Override
	public IDatasource<Ingredient> getIngredients() {
		return ingredientsDatasource;
	}

	@Override
	public IDatasource<Recipe> getRecipes() {
		return recipesDatasource;
	}

	@Override
	public void open() {
		for (IDatasource datasource : datasources) {
			datasource.open();
		}
	}

	@Override
	public void close() {
		for (IDatasource datasource : datasources) {
			datasource.close();
		}
	}
}
