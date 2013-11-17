package com.naderdabour.myrecipebook;

import java.io.IOException;
import java.util.List;

import com.naderdabour.myrecipebook.utils.ImageHelper;
import com.naderdabour.myrecipebook.viewmodels.RecipeSimpleVM;

import android.content.Context;
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

		recipeIdTextView.setText(Long.toString(currentRecipe.getId()));
		recipeNameTextView.setText(currentRecipe.getName());
		
		
		if(currentRecipe.getName().startsWith(RecipesActivity.CATEGORY_IMAGE)){
			int imageResource = getImageRecource(currentRecipe.getImage());
			recipeImageView.setImageResource(imageResource);
		} else {

			String imagePath = currentRecipe.getImage();
			
			try {
				ImageHelper imageHelper = new ImageHelper(context);
				recipeImageView.setImageBitmap(imageHelper.readFile(imagePath));
				
			} catch (IOException e) {
				int categoryImage = getImageRecource(RecipesActivity.CATEGORY_IMAGE + currentRecipe.getCategory().getId());
				recipeImageView.setImageResource(categoryImage);
			} catch (NullPointerException e){
				int categoryImage = getImageRecource(RecipesActivity.CATEGORY_IMAGE + currentRecipe.getCategory().getId());
				recipeImageView.setImageResource(categoryImage);
			}
		}
		
		return theView;
	}
	
	private int getImageRecource(String name){
		
		int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
		return imageResource;
	}
}
