package com.parthamanna.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parthamanna.blog.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
}
