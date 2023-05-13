package com.parthamanna.blog.services;

import java.util.List;

import com.parthamanna.blog.payloads.CategoryDto;

public interface CategoryService {
	//create category
	CategoryDto createCategory(CategoryDto categotyDto);
	//update category
	CategoryDto updateCategory(CategoryDto categotyDto,Integer categoryId);
	//delete category
	void deleteCategory(Integer categoryId);
	//get a category
	CategoryDto getCategory(Integer categoryId);
	//get All category
	List<CategoryDto> getCategories();
}
