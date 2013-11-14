package com.naderdabour.myrecipebook.data;

import java.util.List;

public interface IDatasource<E> {

	void open();
	void close();
	
	E create(E entry);
	int update(E entry);
	List<E> findAll();
	List<E> findFiltered(String selection, String orderBy);
	E findById(long id);
	boolean remove(long id);
	
}
