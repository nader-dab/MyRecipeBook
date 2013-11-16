package com.naderdabour.myrecipebook;

import java.util.List;

import com.naderdabour.myrecipebook.viewmodels.CategoryVM;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryListAdapter extends ArrayAdapter<CategoryVM> {

	private Context context;
	
	public CategoryListAdapter(Context context,  List<CategoryVM> categories) {
		super(context, android.R.layout.simple_list_item_1, categories);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inlater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inlater.inflate(R.layout.category_list_entry, null);

		CategoryVM category = getItem(position);

		TextView categoryIdTextView = (TextView) view.findViewById(R.id.categoryIdTextView);
		TextView categoryNameTextView = (TextView) view.findViewById(R.id.categoryNameTextView);
		
		categoryIdTextView.setText(Long.toString(category.getId()));
		categoryNameTextView.setText(category.getName());

		return view;
	}

}
