package com.naderdabour.myrecipebook.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.naderdabour.myrecipebook.models.Product;

public class ProductDatasource extends GenericDatasource<Product> {

	private static final String[] allColumns = {
		DatabaseHelper.TABLE_PRODUCT_ID,
		DatabaseHelper.TABLE_PRODUCT_NAME
	};
	
	public ProductDatasource(Context context) {
		super(context, DatabaseHelper.TABLE_PRODUCT, allColumns);
	}

	@Override
	protected ContentValues entryToContentValues(Product entry) {
		
		ContentValues values = new ContentValues();
		
		values.put(DatabaseHelper.TABLE_PRODUCT_NAME, entry.getName());
		
		return values;
	}

	@Override
	protected long getEntryId(Product entry) {

		return entry.getId();
	}

	@Override
	protected void setEntryId(Product entry, long insertId) {
		
		entry.setId(insertId);
	}

	@Override
	protected List<Product> cursorToList(Cursor cursor) {
		
		List<Product> products = new ArrayList<Product>();
		if(cursor.getCount() > 0){
			
			while (cursor.moveToNext()) {
				
				long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TABLE_PRODUCT_ID));
				String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TABLE_PRODUCT_NAME));
				
				Product product = new Product(id, name);
				products.add(product);
			}
		}
		return products;
	}
	

}
