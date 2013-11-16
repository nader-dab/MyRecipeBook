package com.naderdabour.myrecipebook.data;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public abstract class GenericDatasource<E> implements IDatasource<E>, IReadable {

	protected DatabaseHelper dbHelper;
	protected SQLiteDatabase database;
	
	private String[] allColumns;
	private String tableName;
	private String primaryKey;
	
	public GenericDatasource(Context context, String tableName, String[] allColumns){
		
		this.dbHelper = DatabaseHelper.getInstance(context);
		this.tableName = tableName;
		this.allColumns = allColumns; 
		this.primaryKey = allColumns[0];
	}

	protected abstract List<E> cursorToList(Cursor cursor);
	
	protected abstract long getEntryId(E entry);
	
	protected abstract ContentValues entryToContentValues(E entry);
	
	protected abstract void setEntryId(E entry, long insertId);
	
	
	@Override
	public List<E> findAll() {
		
		Cursor cursor = database.query(tableName, 
				allColumns, null, null, null, null, null);
		
		List<E> entries = cursorToList(cursor);
		
		return entries;
	}
	
	@Override
	public List<E> findFiltered(String selection, String orderBy) {
		
		Cursor cursor = database.query(tableName, 
				allColumns, selection, null, null, null, orderBy);
		
		List<E> entries = cursorToList(cursor);
		
		return entries;
	}
	
	@Override
	public E findById(long id) {
		
		String where = primaryKey + "=" + id;
		
		Cursor cursor = database.query(tableName, 
				allColumns, where, null, null, null, null);
		
		List<E> entries = cursorToList(cursor);
		
		return entries.size() != 0 ? entries.get(0) : null ;
	}
	
	@Override
	public boolean remove(long id) {
		
		String where = primaryKey + "=" + id;
		Log.v("Removing", "table " + tableName + " id" + id);
		int rowsAffected =  database.delete(tableName, where, null);
		
		return (rowsAffected != 0);
	}
	
	
	@Override
	public E create(E entry) {
		
		ContentValues values = entryToContentValues(entry);

		long insertId = database.insert(tableName, null, values);
		
		setEntryId(entry, insertId);

		return entry;
	}
	
	@Override
	public int update(E entry) {

		ContentValues values = entryToContentValues(entry);

		String where = primaryKey + " = ?";
		
		long entryToUpdateId = getEntryId(entry);
		
		int rowsAffected = database.update(tableName, values,
				where, new String[] { Long.toString(entryToUpdateId) });
		 
		 return rowsAffected;
	}
	
	public void open() {
		
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		
		dbHelper.close();
	}
}
