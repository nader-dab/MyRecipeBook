package com.naderdabour.myrecipebook;

import java.util.ArrayList;
import java.util.List;

import com.naderdabour.myrecipebook.data.DatabaseHelper;
import com.naderdabour.myrecipebook.data.IUowData;
import com.naderdabour.myrecipebook.data.UowData;
import com.naderdabour.myrecipebook.models.*;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	private IUowData uowData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        uowData = new UowData(this);
        uowData.open();
           
        List<Category> categories = uowData.getCategories().findAll();
        
        if(categories.size() == 0){
        	initCategories();
        	categories = uowData.getCategories().findAll();
        }
        
        List<Product> products = uowData.getProducts().findAll();
        
        if(products.size()==0){
        	initProducts();
        	products = uowData.getProducts().findAll();
        }
        
        List<Measurement> measurements = uowData.getMeasurements().findAll();
        if(measurements.size()==0){
        	initMeasurements();
        	measurements = uowData.getMeasurements().findAll();
        }
        
        List<Recipe> recipes = uowData.getRecipes().findAll();
        
        if(recipes.size()== 0){
        	initRecipes();
        	recipes = uowData.getRecipes().findAll();
        }
        
        List<Ingredient> ingredients = uowData.getIngredients().findAll();
        
        if(ingredients.size()== 0){
        	initIngredients();
        	ingredients = uowData.getIngredients().findAll();
        }
        
        for (Ingredient ingredient : ingredients) {
			Log.v("Ingredient", ingredient.toString());
		}
    }
    
	private void initIngredients() {
		
		Ingredient ingredient;

		ingredient = new Ingredient();
		ingredient.setMeasurementId(5);
		ingredient.setProductId(0);
		ingredient.setQuantity(1);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(6);
		ingredient.setProductId(5);
		ingredient.setQuantity(2);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(0);
		ingredient.setProductId(7);
		ingredient.setQuantity(3);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(6);
		ingredient.setProductId(6);
		ingredient.setQuantity(1);
		uowData.getIngredients().create(ingredient);
		
		ingredient = new Ingredient();
		ingredient.setMeasurementId(1);
		ingredient.setProductId(1);
		ingredient.setQuantity(500);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(0);
		ingredient.setProductId(2);
		ingredient.setQuantity(3);
		uowData.getIngredients().create(ingredient);

		ingredient = new Ingredient();
		ingredient.setMeasurementId(6);
		ingredient.setProductId(6);
		ingredient.setQuantity(2);
		uowData.getIngredients().create(ingredient);
		
	}

	private void initRecipes() {

		Recipe recipe;
		
		recipe = new Recipe();
		recipe.setCategoryId(1);
		recipe.setName("Chicken with sweet potatoes");
		recipe.setImage("(None)");
		recipe.setDetails("Everything is put in an oven for 25 minutes. Very tasty.");
		uowData.getRecipes().create(recipe);

		recipe = new Recipe();
		recipe.setCategoryId(4);
		recipe.setName("Egg omllet");
		recipe.setImage("(None)");
		recipe.setDetails("Fry egss in a pan for 5-10 minutes.");
		uowData.getRecipes().create(recipe);
		
	}

	private void initMeasurements() {
		
		Measurement measurement;
		
		measurement = new Measurement();
		measurement.setName("gram(s)");
		uowData.getMeasurements().create(measurement);
		
		measurement = new Measurement();
		measurement.setName("kilogram(s)");
		uowData.getMeasurements().create(measurement);
		
		measurement = new Measurement();
		measurement.setName("liter(s)");
		uowData.getMeasurements().create(measurement);
		
		measurement = new Measurement();
		measurement.setName("mililiter(s)");
		uowData.getMeasurements().create(measurement);
		
		measurement = new Measurement();
		measurement.setName("cup(s)");
		uowData.getMeasurements().create(measurement);
		
		measurement = new Measurement();
		measurement.setName("tablespoon(s)");
		uowData.getMeasurements().create(measurement);
		
		measurement = new Measurement();
		measurement.setName("teaspoon(s)");
		uowData.getMeasurements().create(measurement);
	}

	private void initProducts() {
		
		Product product;
		
		product = new Product();
		product.setName("flour");
		uowData.getProducts().create(product);
		
		product = new Product();
		product.setName("chicken");
		uowData.getProducts().create(product);
		
		product = new Product();
		product.setName("egg");
		uowData.getProducts().create(product);
		
		product = new Product();
		product.setName("sea salt");
		uowData.getProducts().create(product);
		
		product = new Product();
		product.setName("lemon");
		uowData.getProducts().create(product);
		
		product = new Product();
		product.setName("honey mustard");
		uowData.getProducts().create(product);
		
		product = new Product();
		product.setName("extra-virgin olive oil");
		uowData.getProducts().create(product);
		
		product = new Product();
		product.setName("sweet potatoes");
		uowData.getProducts().create(product);
	}

	private void initCategories() {
		
		Category category;
		category = new Category();
		category.setName("Main dish");
		uowData.getCategories().create(category);
		
		category = new Category();
		category.setName("Desert");
		uowData.getCategories().create(category);
		
		category = new Category();
		category.setName("Soup");
		uowData.getCategories().create(category);
		
		category = new Category();
		category.setName("Side dish");
		uowData.getCategories().create(category);
		
		category = new Category();
		category.setName("Snack");
		uowData.getCategories().create(category);
		
		category = new Category();
		category.setName("Salad");
		uowData.getCategories().create(category);
		
		category = new Category();
		category.setName("Dinner");
		uowData.getCategories().create(category);
		
		category = new Category();
		category.setName("Beverage");
		uowData.getCategories().create(category);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	uowData.open();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	uowData.close();
    }
    
    
}
