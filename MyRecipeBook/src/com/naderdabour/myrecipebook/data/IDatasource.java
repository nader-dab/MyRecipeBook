package com.naderdabour.myrecipebook.data;

import java.util.List;

// Specifies the required methods for any Datasource object.
public interface IDatasource<E> {

	E create(E entry);
	int update(E entry);
	List<E> findAll();
	List<E> findFiltered(String selection, String orderBy);
	E findById(long id);
	boolean remove(long id);
	
}
