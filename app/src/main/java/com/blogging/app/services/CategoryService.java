package com.blogging.app.services;

import java.util.List;

import com.blogging.app.payloads.CategoryDTO;

public interface CategoryService {

	//create
	CategoryDTO createCategory(CategoryDTO ctdo);

	//update
	CategoryDTO updateCategory(CategoryDTO ctdo, Integer cid);

	//delete
	void deleteCategory(Integer cid);
	
	//getsingle
	CategoryDTO getCategory(Integer cid);

	//getall
	List<CategoryDTO> getAllCategory();
	
}
