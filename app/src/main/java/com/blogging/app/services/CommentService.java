package com.blogging.app.services;


import com.blogging.app.payloads.CommentDTO;

public interface CommentService {

	CommentDTO createComment(CommentDTO comment, Integer postId);
	
	void deleteComment(Integer commentId);
}
