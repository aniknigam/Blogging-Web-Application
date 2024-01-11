package com.blogging.app.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	  
	 
		private int id;
		
		@NotEmpty
		private String name;
		
	    @Email(message = "Email is not valid")
		private String email; 
	    
		@NotEmpty
		@Size(min = 8, message = "Password must be of 8 characters")
		private String password;
		
		@NotEmpty
		private String about;
		
		@NotEmpty
		private String roles;
}

