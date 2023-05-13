package com.parthamanna.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parthamanna.blog.entities.Category;
import com.parthamanna.blog.exceptions.ResourceNotFoundException;
import com.parthamanna.blog.payloads.CategoryDto;
import com.parthamanna.blog.repositories.CategotyRepository;
import com.parthamanna.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategotyRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categotyDto) {
		Category category=this.modelMapper.map(categotyDto, Category.class);
		this.categoryRepository.save(category);
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categotyDto, Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", "Category Id ", categoryId));
		
		category.setCategoryTitle(categotyDto.getCategoryTitle());
		category.setCategoryDescription(categotyDto.getCategoryDescription());
		
		Category updatedCategory=this.categoryRepository.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", "Category Id ", categoryId));
		this.categoryRepository.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", "Category Id ", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepository.findAll();
		List<CategoryDto> categotyDtos = categories.stream().map((category)->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categotyDtos;
	}

}
