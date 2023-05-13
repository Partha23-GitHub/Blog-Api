package com.parthamanna.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parthamanna.blog.payloads.ApiResponse;
import com.parthamanna.blog.payloads.CommentDto;
import com.parthamanna.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto comment,@PathVariable Integer postId){
		CommentDto createdComment=this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createdComment,HttpStatus.CREATED);
	}
	@PutMapping("/{commentId}")
	public ResponseEntity<CommentDto>updateComment(@RequestBody CommentDto comment,@PathVariable Integer commentId){
		CommentDto updatedComment=this.commentService.editComment(comment, commentId);
		return new ResponseEntity<CommentDto>(updatedComment,HttpStatus.OK);
	}
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
	}
}
