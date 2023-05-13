package com.parthamanna.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parthamanna.blog.entities.Comment;
import com.parthamanna.blog.entities.Posts;
import com.parthamanna.blog.exceptions.ResourceNotFoundException;
import com.parthamanna.blog.payloads.CommentDto;
import com.parthamanna.blog.repositories.CommentRepository;
import com.parthamanna.blog.repositories.PostRepository;
import com.parthamanna.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postReporitory;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Posts post = this.postReporitory.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "postId", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepository.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public CommentDto editComment(CommentDto commentDto, Integer commentId) {
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Comment Id", commentId));
		comment.setContent(commentDto.getContent());
		Comment updatedComment = this.commentRepository.save(comment);
		return this.modelMapper.map(updatedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Comment Id", commentId));
		this.commentRepository.delete(comment);

	}

}
