package com.blogging.app.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogging.app.entities.Category;
import com.blogging.app.entities.Post;
import com.blogging.app.entities.User;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payloads.PostDTO;
import com.blogging.app.payloads.PostResponse;
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
	  post.setUser(user);//all the details of user will be added
	  post.setCategory(category);//all the details of category will be added
	  
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
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize) {
		//Pageable is an interface in Spring Data that represents pagination information.
		Pageable p = PageRequest.of(pageNumber, pageSize);		
		
		Page<Post> pagePost = this.postrepo.findAll(p);
		
		//The getContent method of the Page object retrieves the list of Post entities within the current page.
		List<Post> posts = pagePost.getContent();
		List<PostDTO> postdtos = posts
				.stream().map((post) -> this.modelmapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		
		PostResponse postresponse = new PostResponse();
		postresponse.setContent(postdtos);
		postresponse.setPageNumber(pagePost.getNumber());
		postresponse.setPageSize(pagePost.getSize());
		postresponse.setTotalElement(pagePost.getTotalElements());
		postresponse.setTotalPages(pagePost.getTotalPages());
		postresponse.setLastPage(pagePost.isLast());
		
		return postresponse;
	}

	@Override
	public PostDTO getSinglePost(Integer postId) {
		Post post = this.postrepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","Post Id", postId));
		PostDTO postdto = this.modelmapper.map(post, PostDTO.class);
		return postdto;
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
		Category ctg = this.categoryrepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
	    Pageable p = PageRequest.of(pageNumber, pageSize);
	    Page<Post> pagePost = this.postrepo.findByCategory(ctg, p);
		
		List<PostDTO> postsdto = pagePost.getContent()
				   .stream().map((post) -> this.modelmapper.map(post, PostDTO.class))
				   .collect(Collectors.toList());
		PostResponse postresponse = new PostResponse();
	    postresponse.setContent(postsdto);
	    postresponse.setPageNumber(pagePost.getNumber());
	    postresponse.setPageSize(pagePost.getSize());
	    postresponse.setTotalElement(pagePost.getTotalElements());
	    postresponse.setTotalPages(pagePost.getTotalPages());
	    postresponse.setLastPage(pagePost.isLast());
		return postresponse ;
	}

	@Override
	public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		User user = this.userrepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		 Pageable p = PageRequest.of(pageNumber, pageSize);
		 Page<Post> pagePost = this.postrepo.findByUser(user, p);
		
	
		List<PostDTO> postdto = pagePost.getContent()
				    .stream().map((e) -> this.modelmapper.map(e, PostDTO.class)).collect(Collectors.toList());
		
		PostResponse postresponse = new PostResponse();
	    postresponse.setContent(postdto);
	    postresponse.setPageNumber(pagePost.getNumber());
	    postresponse.setPageSize(pagePost.getSize());
	    postresponse.setTotalElement(pagePost.getTotalElements());
	    postresponse.setTotalPages(pagePost.getTotalPages());
	    postresponse.setLastPage(pagePost.isLast());
		return postresponse ;
	}

	@Override
	public List<Post> searchPost(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
