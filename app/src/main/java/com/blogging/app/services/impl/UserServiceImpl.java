package com.blogging.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.app.entities.User;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payloads.UserDTO;
import com.blogging.app.repositories.UserRepo;
import com.blogging.app.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
	private UserRepo userRepo;
    
    @Autowired
    private ModelMapper modelMapper;

	@Override
	public UserDTO createUser(UserDTO userdto) {
		User user = this.dtoToUser(userdto);
		User savedUser = this.userRepo.save(user);
		return this.userTodto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userdto, Integer userId) {
		//here orElseThrow method is part of the Optional class in Java. The Optional class was introduced in Java 8 as a way to handle potentially null values without explicitly using null references.
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
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return this.userTodto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		//in this line we are converting each data from user type to user dto type, we are doing it for n number of data that is why we are using stream, stream is a Api provides a powerful and expressive way to process collections of objects. Streams in Java are not data structures themselves but rather a mechanism for expressing computations on data.
		List<UserDTO> userDtos = users.stream().map(user->this.userTodto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
	 User user =  this.userRepo.findById(userId)
	         .orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
	  this.userRepo.delete(user);

	}
	
	private User dtoToUser(UserDTO userDto) {
//		User user = new User();		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		//you can do the above thing with modelMapper also read about ModelMapper int the docs
		
		User user = this.modelMapper.map(userDto, User.class);
		
		
		
		return user;		
	}
	
	public UserDTO userTodto(User user) {
//		UserDTO userdto = new UserDTO();
//		
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setAbout(user.getAbout());
//		userdto.setPassword(user.getPassword());
		
		UserDTO userdto = this.modelMapper.map(user, UserDTO.class);
		
		return userdto;		
	}

}
