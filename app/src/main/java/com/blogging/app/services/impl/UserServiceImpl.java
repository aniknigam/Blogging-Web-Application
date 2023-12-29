package com.blogging.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.blogging.app.entities.User;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payloads.UserDTO;
import com.blogging.app.repositories.UserRepo;
import com.blogging.app.services.UserService;

public class UserServiceImpl implements UserService {
	
    @Autowired
	private UserRepo userRepo;

	@Override
	public UserDTO createUser(UserDTO userdto) {
		User user = this.dtoToUser(userdto);
		User savedUser = this.userRepo.save(user);
		return this.userTodto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userdto, Integer userId) {
		//he orElseThrow method is part of the Optional class in Java. The Optional class was introduced in Java 8 as a way to handle potentially null values without explicitly using null references.
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setAbout(userdto.getAbout());
		user.setPassword(userdto.getPassword());
		
		User updatedUser = this.userRepo.save(user);
		UserDTO userdto1 = this.userTodto(updatedUser);
		return userdto1;
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub

	}
	
	private User dtoToUser(UserDTO userDto) {
		User user = new User();
		
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		return user;		
	}
	
	public UserDTO userTodto(User user) {
		UserDTO userdto = new UserDTO();
		
		userdto.setId(user.getId());
		userdto.setName(user.getName());
		userdto.setEmail(user.getEmail());
		userdto.setAbout(user.getAbout());
		userdto.setPassword(user.getPassword());
		
		return userdto;		
	}

}
