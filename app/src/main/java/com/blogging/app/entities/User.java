package com.blogging.app.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
    @Column(name = "user_name", nullable = false, length = 100)   
	private String name;
    
    @Column
    @NotBlank(message = "email cannot be blank")
	private String email;
    
    @Column
	private String password;
    
    @Column
	private String about;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Post> posts = new ArrayList<>();
    //CascadeType.ALL ensures that any changes to the Category will be cascaded to associated posts.
   // FetchType.LAZY indicates that posts are loaded lazily, only essential information will be loaded, if it is set to eager all the information will be loaded.

}
