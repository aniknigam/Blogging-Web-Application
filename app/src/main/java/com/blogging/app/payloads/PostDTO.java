package com.blogging.app.payloads;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
	
	private Integer postId;

	@NotEmpty
	private String title;
	
	@NotEmpty
	private String content;
	
	
	private String imageName;
	
	
	private Date addDate;
	
	
	private CategoryDTO category;
	
	
	private UserDTO user;
}
