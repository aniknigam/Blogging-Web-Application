package com.blogging.app.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.app.entities.Category;
import com.blogging.app.entities.Post;
import com.blogging.app.entities.User;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payloads.PostDTO;
import com.blogging.app.repositories.CategoryRepo;
import com.blogging.app.repositories.PostRepo;
import com.blogging.app.repositories.UserRepo;
import com.blogging.app.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postrepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private CategoryRepo categoryrepo;

	@Override
	public PostDTO createPost(PostDTO postdto, Integer userId, Integer categoryId) {
	  User user = this.userrepo.findById(userId)
			  .orElseThrow(() -> new ResourceNotFoundException("User","User Id", userId));
	  
	  Category category = this.categoryrepo.findById(categoryId)
			             .orElseThrow(() -> new ResourceNotFoundException("Category","Category Id", categoryId));
	  
	  Post post =  this.modelmapper.map(postdto, Post.class);
	  post.setImageName("default.png");
	  post.setAddDate(new Date());
	  post.setUser(user);
	  post.setCategory(category);
	  
	  Post savePost = this.postrepo.save(post);
	  return this.modelmapper.map(savePost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postdto, Integer postId) {
		Post post = this.postrepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id", postId));
		
		post.setTitle(postdto.getTitle());
		post.setContent(postdto.getContent());
		post.setImageName(postdto.getImageName());
		
		Post updatedpost = this.postrepo.save(post);
		return this.modelmapper.map(updatedpost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postrepo.findById(postId)
		.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id", postId));
		
		this.postrepo.delete(post);
	}

	@Override
	public List<PostDTO> getAllPost() {
		List<Post> posts = this.postrepo.findAll();
		List<PostDTO> postdtos = posts
				.stream().map((post) -> this.modelmapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postdtos;
	}

	@Override
	public PostDTO getSinglePost(Integer postId) {
		Post post = this.postrepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id", postId));
		PostDTO postdto = this.modelmapper.map(post, PostDTO.class);
		return postdto;
	}

	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		Category ctg = this.categoryrepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		List<Post> posts = this.postrepo.findByCategory(ctg);
		List<PostDTO> postsdto = posts.stream().
				map((post) -> this.modelmapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return postsdto;
	}

	@Override
	public List<PostDTO> getPostsByUser(Integer userId) {
		User user = this.userrepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		
		List<Post> posts = this.postrepo.findByUser(user);
		List<PostDTO> postdto = posts.stream().map((e) -> this.modelmapper.map(e, PostDTO.class)).collect(Collectors.toList());
		return postdto ;
	}

	@Override
	public List<Post> searchPost(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
