package com.parthamanna.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parthamanna.blog.entities.Category;
import com.parthamanna.blog.entities.Posts;
import com.parthamanna.blog.entities.User;

public interface PostRepository extends JpaRepository<Posts,Integer> {
	//extracting all post of a user
	List<Posts>findByUser(User user);
	//extracting all category
	List<Posts>findByCategory(Category category);
	
	List<Posts> findByTitleContains(String title);
}
