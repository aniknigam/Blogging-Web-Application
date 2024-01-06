package com.blogging.app.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="blog post")
@NoArgsConstructor
@Getter
@Setter
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name = "post_title", length = 100, nullable = false)
	private String title;
	
	@Column(length = 10000)
	private String content;
	
	@Column
	private String imageName;
	
	@Column
	private Date addDate;
	
	@ManyToOne
	@JoinColumn(name = "CategoryId")//it is used to change the name of the column which is created after mapping
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;
	
	@OneToMany(mappedBy="post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Comment> comments = new HashSet<>();
}
