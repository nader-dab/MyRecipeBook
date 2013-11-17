package com.naderdabour.myrecipebook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static DatabaseHelper databaseHelperInstance = null;
	
	private static final String DATABASE_NAME = "myrecipebook.db";
	private static final int DATABASE_VERSION = 10;
	
	public static final String TABLE_CATEGORY = "categories";
	public static final String TABLE_CATEGORY_ID = "categoryId";
	public static final String TABLE_CATEGORY_NAME = "categoryName";
	private static final String TABLE_CATEGORY_CREATE = 
			"CREATE TABLE " + TABLE_CATEGORY + " (" +
			TABLE_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			TABLE_CATEGORY_NAME + " TEXT " +
			")";
	
	public static final String TABLE_MEASUREMENT = "measurements";
	public static final String TABLE_MEASUREMENT_ID = "measurementId";
	public static final String TABLE_MEASUREMENT_NAME = "measurementName";
	private static final String TABLE_MEASUREMENT_CREATE = 
			"CREATE TABLE " + TABLE_MEASUREMENT + " (" +
			TABLE_MEASUREMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			TABLE_MEASUREMENT_NAME + " TEXT " +
			")";
	
	public static final String TABLE_PRODUCT = "products";
	public static final String TABLE_PRODUCT_ID = "productId";
	public static final String TABLE_PRODUCT_NAME = "productName";
	private static final String TABLE_PRODUCT_CREATE =
			"CREATE TABLE " +  TABLE_PRODUCT + " (" +
			TABLE_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			TABLE_PRODUCT_NAME + " TEXT " + 
			")";
	
	public static final String TABLE_RECIPE = "recipes";
	public static final String TABLE_RECIPE_ID = "recipeId";
	public static final String TABLE_RECIPE_CATEGORY_ID = "categoryId";
	public static final String TABLE_RECIPE_NAME = "recipeName";
	public static final String TABLE_RECIPE_DETAILS = "recipeDetails";
	public static final String TABLE_RECIPE_IMAGE = "recipeImage";
	private static final String TABLE_RECIPE_CREATE = 
			"CREATE TABLE " + TABLE_RECIPE + " (" +
			TABLE_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			TABLE_RECIPE_CATEGORY_ID + " INTEGER, " +
			TABLE_RECIPE_NAME + " TEXT, " +
			TABLE_RECIPE_DETAILS + " TEXT, " + 
			TABLE_RECIPE_IMAGE + " TEXT, " +
			"FOREIGN KEY(" + TABLE_RECIPE_CATEGORY_ID +") REFERENCES " + TABLE_CATEGORY +"(" + TABLE_CATEGORY_ID + ")" +
			")";
	
	public static final String TABLE_INGREDIENT = "ingredients";
	public static final String TABLE_INGREDIENT_ID = "ingredientId";
	public static final String TABLE_INGREDIENT_PRODUCT_ID = "productId";
	public static final String TABLE_INGREDIENT_MEASUREMENT_ID = "measurementId";
	public static final String TABLE_INGREDIENT_RECIPE_ID = "recipeId";
	public static final String TABLE_INGREDIENT_QUANTITY = "quantity";
	private static final String TABLE_INGREDIENT_CREATE = 
			"CREATE TABLE " + TABLE_INGREDIENT + " (" +
			TABLE_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			TABLE_INGREDIENT_PRODUCT_ID + " INTEGER, " +
			TABLE_INGREDIENT_MEASUREMENT_ID + " INTEGER, " +
			TABLE_INGREDIENT_RECIPE_ID + " INTEGER, " +
			TABLE_INGREDIENT_QUANTITY + " REAL, " +
			"FOREIGN KEY(" + TABLE_INGREDIENT_PRODUCT_ID +") REFERENCES " + TABLE_PRODUCT + "(" + TABLE_PRODUCT_ID + "), " +
			"FOREIGN KEY(" + TABLE_INGREDIENT_MEASUREMENT_ID +") REFERENCES " + TABLE_MEASUREMENT + "(" + TABLE_MEASUREMENT_ID + "), " +
			"FOREIGN KEY(" + TABLE_INGREDIENT_RECIPE_ID +") REFERENCES " + TABLE_RECIPE + "(" + TABLE_RECIPE_ID + ")" +
			")";

	// Singleton instance to ensure only one database instance for the entire
	// application's lifecycle.
	public static DatabaseHelper getInstance(Context context){
		
		if(databaseHelperInstance == null){
			databaseHelperInstance = new DatabaseHelper(context);
		}
		
		return databaseHelperInstance;
	}
	
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CATEGORY_CREATE);
		db.execSQL(TABLE_MEASUREMENT_CREATE);
		db.execSQL(TABLE_PRODUCT_CREATE);
		db.execSQL(TABLE_RECIPE_CREATE);
		db.execSQL(TABLE_INGREDIENT_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASUREMENT);		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);		
		onCreate(db);
	}

}
