package com.naderdabour.myrecipebook;

import java.util.ArrayList;
import java.util.List;

import com.naderdabour.myrecipebook.data.IUowData;
import com.naderdabour.myrecipebook.data.UowData;
import com.naderdabour.myrecipebook.models.*;
import com.naderdabour.myrecipebook.utils.DbInitializer;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	private UowData uowData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        uowData = new UowData(this);
        uowData.open();
        
        DbInitializer.init(uowData); 
        
        List<Category> categories = uowData.getCategories().findAll();
        
        List<Product> products = uowData.getProducts().findAll();

        List<Measurement> measurements = uowData.getMeasurements().findAll();
        
        List<Ingredient> ingredients = uowData.getIngredients().findAll();

        for (Ingredient ingredient : ingredients) {
			Log.v("Ingredient", ingredient.toString());
		}
        
        List<Recipe> recipes = uowData.getRecipes().findAll();
        
        for (Recipe recipe : recipes) {
			Log.v("Recipe", recipe.toString());
		}
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
