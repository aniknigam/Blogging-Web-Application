package com.blogging.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogging.app.payloads.ApiResponse;
import com.blogging.app.payloads.PostDTO;
import com.blogging.app.payloads.PostResponse;
import com.blogging.app.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService postservice;
	
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDTO> createPost(
			@Valid
			@RequestBody PostDTO postdto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId)
	{
		PostDTO createpost = this.postservice.createPost(postdto, userId, categoryId);
		return new ResponseEntity<PostDTO>(createpost,HttpStatus.CREATED);
	}
	
	//get all post of a particular user	
	@GetMapping("/user/posts/{userId}")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId){
		List<PostDTO> posts = this.postservice.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
	}
	
	//get all post of a particular category	
	@GetMapping("/category/posts/{categoryId}")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDTO> posts = this.postservice.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
	}
	
	//get all posts
	@GetMapping("/allposts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue="0", required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="5", required = false) Integer pageSize			
			)
	{
		PostResponse allposts = this.postservice.getAllPost(pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(allposts,HttpStatus.OK);
	}
	
	//get post by id
	@GetMapping("/getpost/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId){
		PostDTO postdto = this.postservice.getSinglePost(postId);
		return new ResponseEntity<PostDTO>(postdto, HttpStatus.OK);
	}
	
	//delete a post
	@DeleteMapping("/delete/{postId}")
	public ApiResponse deletepost(@PathVariable Integer postId) {
		this.postservice.deletePost(postId);
		return new ApiResponse("Post is deleted Successfully !!", true, "You can create a new post");
	}
	
	//update a post
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDTO> updatePost(
			@RequestBody PostDTO postdto,
			@PathVariable Integer postId
			)
	{
		PostDTO updatePost = this.postservice.updatePost(postdto, postId);
		
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
	}



	
}
