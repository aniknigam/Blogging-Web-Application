package com.blogging.app.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogging.app.payloads.ApiResponse;
import com.blogging.app.payloads.PostDTO;
import com.blogging.app.payloads.PostResponse;
import com.blogging.app.services.PostService;
import com.blogging.app.services.FileService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService postservice;
	
	@Autowired
	private FileService fileservice;
	
	// is a Spring Framework annotation-based configuration that injects the value of the property project.image
	//You can check in application.properties it is  images/
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postdto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDTO createpost = this.postservice.createPost(postdto, userId, categoryId);
		return new ResponseEntity<PostDTO>(createpost, HttpStatus.CREATED);
	}

	// get all post of a particular user
	@GetMapping("/user/posts/{userId}")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PostResponse posts = this.postservice.getPostsByUser(userId, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	// get all post of a particular category
	@GetMapping("/category/posts/{categoryId}")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PostResponse posts = this.postservice.getPostsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	// get all posts
	@GetMapping("/allposts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PostResponse allposts = this.postservice.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(allposts, HttpStatus.OK);
	}

	// get post by id
	@GetMapping("/getpost/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
		PostDTO postdto = this.postservice.getSinglePost(postId);
		return new ResponseEntity<PostDTO>(postdto, HttpStatus.OK);
	}

	// delete a post
	@DeleteMapping("/delete/{postId}")
	public ApiResponse deletepost(@PathVariable Integer postId) {
		this.postservice.deletePost(postId);
		return new ApiResponse("Post is deleted Successfully !!", true, "You can create a new post");
	}

	// update a post

	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postdto, @PathVariable Integer postId) {
		PostDTO updatePost = this.postservice.updatePost(postdto, postId);

		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
	}

	// search a post
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchPost(@PathVariable("keywords") String keywords) {
		List<PostDTO> results = this.postservice.searchPost(keywords);
		return new ResponseEntity<List<PostDTO>>(results, HttpStatus.OK);
	}
	
	
	//post image
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId
			) throws IOException
	{	
		PostDTO postdto = this.postservice.getSinglePost(postId);
		String filename = this.fileservice.uploadImage(path, image);//it return the name of the image in hash form
		postdto.setImageName(filename);
		PostDTO updatedpost = this.postservice.updatePost(postdto, postId);
		return new ResponseEntity<PostDTO>(updatedpost, HttpStatus.OK);
	}
	
   //method to serve file
	//it will produce a response with content type set to JPEG image. 
	@GetMapping(value="/getImage/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response // it means that it will return the response to the browser in the form of image
			) throws IOException
	{
		InputStream resource = this.fileservice.getResource(path, imageName);// it will get the image 
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);//it is telling the response that it will be of type image
		StreamUtils.copy(resource, response.getOutputStream());	//it is sending back the reponse to the browser
		//StreamUtils is a utility class provided by Spring Framework that simplifies working with Java streams. 
		//It is part of the org.springframework.util package and offers various methods for efficiently dealing with input and output streams.
		//StreamUtils.copy(InputStream source, OutputStream destination):
		//This method copies data from the InputStream (source) to the OutputStream (destination).
		//It reads data from the source stream and writes it to the destination stream until there's no more data to copy.
	}
	

}
