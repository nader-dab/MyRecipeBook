package com.naderdabour.myrecipebook;

import java.util.ArrayList;
import java.util.List;

import com.naderdabour.myrecipebook.data.DatabaseHelper;
import com.naderdabour.myrecipebook.data.IReadable;
import com.naderdabour.myrecipebook.data.IUowData;
import com.naderdabour.myrecipebook.data.UowData;
import com.naderdabour.myrecipebook.models.*;
import com.naderdabour.myrecipebook.utils.DbInitializer;
import com.naderdabour.myrecipebook.utils.ViewModelHelper;
import com.naderdabour.myrecipebook.viewmodels.*;

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

		((IReadable) uowData).open();

		DbInitializer.init(uowData); 
		
		ViewModelHelper vmHelper = new ViewModelHelper(uowData);
		
		for (RecipeSimpleVM rec : vmHelper.getRecipeSimpleVM(uowData.getRecipes().findAll())) {
			Log.v("Recipies", rec.toString());
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
		((IReadable) uowData).open();
	}

	@Override
	protected void onPause() {
		super.onPause();
		((IReadable) uowData).close();
	}
}
