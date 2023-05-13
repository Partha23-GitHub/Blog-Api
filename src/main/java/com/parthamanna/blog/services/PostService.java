package com.parthamanna.blog.services;

import java.util.List;

import com.parthamanna.blog.payloads.PostDto;
import com.parthamanna.blog.payloads.PostResponse;

public interface PostService {
	
	//create post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update post
	PostDto updatePostById(PostDto postDto,Integer postId);
	
	//delete Post
	void deletePostById(Integer postId);
	
	//get all post
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDirection);
	
	//get a single post
	PostDto getPostById(Integer postId);
	
	//get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all post by user
	List<PostDto>getPostsByUser(Integer userId);
	
	//search posts
	List<PostDto>searchPosts(String keykord);		
	
}
