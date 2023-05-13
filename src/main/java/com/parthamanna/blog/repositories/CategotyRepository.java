package com.parthamanna.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parthamanna.blog.entities.Category;

public interface CategotyRepository extends JpaRepository<Category, Integer>{

}
