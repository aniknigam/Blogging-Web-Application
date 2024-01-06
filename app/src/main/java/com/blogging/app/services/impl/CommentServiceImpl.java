package com.blogging.app.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.app.entities.Comment;
import com.blogging.app.entities.Post;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payloads.CommentDTO;
import com.blogging.app.repositories.CommentRepo;
import com.blogging.app.repositories.PostRepo;
import com.blogging.app.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postrepo;
	
	@Autowired
	private CommentRepo commentrepo;
	
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public CommentDTO createComment(CommentDTO comment, Integer postId) {
		 Post post = this.postrepo.findById(postId)
				 .orElseThrow(() -> new ResourceNotFoundException("Post","Post Id", postId));
		 
		Comment com =  this.modelmapper.map(comment, Comment.class);
		com.setPost(post);
		Comment save = this.commentrepo.save(com);
		
		return this.modelmapper.map(save, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com = this.commentrepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id", commentId));
		this.commentrepo.delete(com);

	}

}
