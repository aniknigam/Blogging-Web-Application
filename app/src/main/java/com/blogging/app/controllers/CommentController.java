package com.blogging.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogging.app.payloads.ApiResponse;
import com.blogging.app.payloads.CommentDTO;
import com.blogging.app.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

	@Autowired
	private CommentService commentservice;
	
	@PostMapping("/addcomment/topost/{postId}")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentdto, @PathVariable("postId") Integer postID){
		CommentDTO comment = this.commentservice.createComment(commentdto, postID);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deletcomment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
			this.commentservice.deleteComment(commentId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Message Deleted Successfully", true, "Enjoy Reading"), HttpStatus.OK);		
					
		
	}
	
}
