package com.blogging.app.payloads;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
	
	
	private Integer commentID;

	
	private String content;
}
