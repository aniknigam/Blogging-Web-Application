package com.blogging.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogging.app.entities.AuthRequest;
import com.blogging.app.entities.User;
import com.blogging.app.payloads.ApiResponse;
import com.blogging.app.services.impl.JwtService;
import com.blogging.app.services.impl.UserServiceImpleSec;


@RestController
@RequestMapping("/auth")
public class UserControllerSec {
	 @Autowired
	    private UserServiceImpleSec userInfoService;
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    @Autowired
	    private JwtService jwtService;

	    @GetMapping("/welcome")
	    public String welcome(){
	        return "Welcome to Bloggin Web Application!!";
	    } 

	    
	    @PostMapping("/login")
	    public ResponseEntity<ApiResponse> addUser(@RequestBody AuthRequest authRequest) {
	        try {
	            Authentication authenticate = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

	            if (authenticate.isAuthenticated()) {
	                String token = jwtService.generateToken(authRequest.getUserName());
	                
	                ApiResponse response = new ApiResponse();
	                response.setMessage("Login successful");
	                response.setSuccess(true);
	                response.setAdvice("You Token:  " + token);

	                return  ResponseEntity.ok(response);
	            } else {
	                throw new UsernameNotFoundException("Invalid user request");
	            }
	        } catch (AuthenticationException e) {
	            // Handle authentication failure
	            ApiResponse response = new ApiResponse();
	            response.setMessage("Authentication failed");
	            response.setSuccess(false);
	            response.setAdvice("Invalid credentials");

	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        } catch (Exception e) {
	            // Handle other exceptions
	            ApiResponse response = new ApiResponse();
	            response.setMessage("Internal server error");
	            response.setSuccess(false);
	            response.setAdvice("An unexpected error occurred");

	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }
	   
	    
	  
	}
