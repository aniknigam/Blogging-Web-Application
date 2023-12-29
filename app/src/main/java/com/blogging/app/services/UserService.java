package com.blogging.app.services;

import java.util.List;

import com.blogging.app.payloads.UserDTO;

public interface UserService {

	UserDTO createUser(UserDTO user);
	
	UserDTO updateUser(UserDTO user, Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer userId);
}
