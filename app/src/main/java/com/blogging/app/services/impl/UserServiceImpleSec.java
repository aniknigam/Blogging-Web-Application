package com.blogging.app.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogging.app.entities.User;
import com.blogging.app.repositories.UserRepo;


@Service
public class UserServiceImpleSec  implements UserDetailsService {

	@Autowired
    private UserRepo userRepo;
    
	@Bean
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder();
	}
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepo.findByName(username);
        return userInfo.map(UserDetail::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"+username));
    }
    
    public String addUser(User userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepo.save(userInfo);
        return "User added successfully";
    }
    public List<User> getAllUser(){
         return userRepo.findAll();
    }
    public User getUser(Integer id){
        return userRepo.findById(id).get();
    }

}
