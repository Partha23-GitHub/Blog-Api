package com.parthamanna.blog.services;

import com.parthamanna.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto,Integer postId);
	CommentDto editComment(CommentDto commentDto,Integer commentId);
	void deleteComment(Integer commentId);
}
