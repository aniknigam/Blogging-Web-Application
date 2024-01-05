package com.blogging.app.services;

import java.util.List;


import com.blogging.app.payloads.PostDTO;
import com.blogging.app.payloads.PostResponse;

public interface PostService {

	// create
	PostDTO createPost(PostDTO postdto, Integer userId, Integer categoryId);

	// update
	PostDTO updatePost(PostDTO postdto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get all post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get single post
	PostDTO getSinglePost(Integer postId);

	// get all post by category
	PostResponse getPostsByCategory(Integer categoryId ,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get all post by user
	PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// search post
	List<PostDTO> searchPost(String keyword);
}
