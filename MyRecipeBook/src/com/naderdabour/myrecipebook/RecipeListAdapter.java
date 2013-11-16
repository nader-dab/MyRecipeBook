package com.naderdabour.myrecipebook;

import java.util.List;

import com.naderdabour.myrecipebook.viewmodels.RecipeSimpleVM;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeListAdapter extends ArrayAdapter<RecipeSimpleVM> {
	
	private Context context;
	
	
	public RecipeListAdapter(Context context, List<RecipeSimpleVM> recipes) {
		super(context, android.R.layout.simple_list_item_1, recipes);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View theView = inflater.inflate(R.layout.recipe_list_entry, null);
		
		RecipeSimpleVM currentRecipe = getItem(position);
		
		TextView recipeIdTextView = (TextView) theView.findViewById(R.id.recipeIdTextView);
		TextView recipeNameTextView = (TextView) theView.findViewById(R.id.recipeNameTextView);
		ImageView recipeImageView = (ImageView) theView.findViewById(R.id.recipeImageView);
		
		recipeIdTextView.setText(currentRecipe.getId() + "");
		recipeNameTextView.setText(currentRecipe.getName());

		int imageResource = getImageRecource(currentRecipe.getImage());
		int categoryImage = getImageRecource(MainActivity.CATEGORY_IMAGE + currentRecipe.getCategory().getId());

		if(imageResource != 0){
			recipeImageView.setImageResource(imageResource);
		} else if(categoryImage != 0){
			recipeImageView.setImageResource(categoryImage);
		}
		
		return theView;
	}
	
	private int getImageRecource(String name){
		int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
		return imageResource;
	}
}
