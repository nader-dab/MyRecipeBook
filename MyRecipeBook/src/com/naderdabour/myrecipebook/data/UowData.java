package com.naderdabour.myrecipebook.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.naderdabour.myrecipebook.models.Category;
import com.naderdabour.myrecipebook.models.Ingredient;
import com.naderdabour.myrecipebook.models.Measurement;
import com.naderdabour.myrecipebook.models.Product;
import com.naderdabour.myrecipebook.models.Recipe;

public class UowData implements IUowData, IReadable {

	private IDatasource<Category> categoriesDatasource;
	private IDatasource<Product> productsDatasource;
	private IDatasource<Measurement> measurementDatasource;
	private IDatasource<Ingredient> ingredientsDatasource;
	private IDatasource<Recipe> recipesDatasource;
	private List<IReadable> datasources;
	
	public UowData(Context context){
		categoriesDatasource = new CategoryDatasource(context); 
		productsDatasource = new ProductDatasource(context); 
		measurementDatasource = new MeasurementDatasource(context); 
		ingredientsDatasource = new IngredientDatasource(context); 
		recipesDatasource = new RecipeDatasource(context); 
		
		datasources = new ArrayList<IReadable>();
		
		datasources.add((IReadable)categoriesDatasource);
		datasources.add((IReadable)productsDatasource);
		datasources.add((IReadable)measurementDatasource);
		datasources.add((IReadable)ingredientsDatasource);
		datasources.add((IReadable)recipesDatasource);
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

	public void open() {
		for (IReadable datasource : datasources) {
			datasource.open();
		}
	}

	public void close() {
		for (IReadable datasource : datasources) {
			datasource.close();
		}
	}
}
