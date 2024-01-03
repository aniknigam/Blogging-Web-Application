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

import org.springframework.web.bind.annotation.RestController;

import com.blogging.app.payloads.ApiResponse;
import com.blogging.app.payloads.UserDTO;
import com.blogging.app.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userservice;
	
	//creating a new user
	@PostMapping("/create") //@Valid is used for validation
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userdto){
		UserDTO createuser = this.userservice.createUser(userdto);
		return new ResponseEntity<>(createuser, HttpStatus.CREATED);
		
	}
	
	//updating an existing user
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userdto,@PathVariable("userId") Integer userid){
		UserDTO  updatedUser = this.userservice.updateUser(userdto, userid);
		return ResponseEntity.ok(updatedUser);
	}
	
	//deleting an user
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("userId") Integer uid){
		this.userservice.deleteUser(uid);
		return new  ResponseEntity<ApiResponse>(new ApiResponse("User is deleted", true, "You can create a new user!"), HttpStatus.OK);		
	}
	
	@GetMapping("/allusers")
	public ResponseEntity<List<UserDTO>> allUsers() {	
		return ResponseEntity.ok(this.userservice.getAllUsers());				
	}
	
	@GetMapping("/getuser/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Integer userId){
		return ResponseEntity.ok(this.userservice.getUserById(userId));
	}
	
}
