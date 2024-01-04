package com.blogging.app.services;

import java.util.List;

import com.blogging.app.entities.Post;
import com.blogging.app.payloads.PostDTO;

public interface PostService {

	//create
	PostDTO createPost(PostDTO postdto, Integer userId, Integer categoryId);
	
	//update
	PostDTO updatePost(PostDTO postdto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all post
	List<PostDTO> getAllPost();
	
	//get single post
	PostDTO getSinglePost(Integer postId);
	
	//get all post by category
	List<PostDTO> getPostsByCategory(Integer categoryId);
	
	//get all post by user
	List<PostDTO> getPostsByUser(Integer userId);
	
	//search post
	List<Post> searchPost(String keyword);
}
