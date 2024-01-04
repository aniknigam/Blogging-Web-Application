package com.blogging.app.services;

import java.util.List;

import com.blogging.app.entities.Post;
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
	PostResponse getAllPost(Integer pageNumber, Integer pageSize);

	// get single post
	PostDTO getSinglePost(Integer postId);

	// get all post by category
	List<PostDTO> getPostsByCategory(Integer categoryId);

	// get all post by user
	List<PostDTO> getPostsByUser(Integer userId);

	// search post
	List<Post> searchPost(String keyword);
}
