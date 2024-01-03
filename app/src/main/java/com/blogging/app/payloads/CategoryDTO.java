package com.blogging.app.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

	private Integer categoryId;
	
	@NotBlank
	@Size(min=4, message="Minimum Size of Category Title is 4")
	private String title;
	
	@NotBlank
	@Size(min=10, message="Minimum Size of Category Description is 4")
	private String categoryDescription;
}
