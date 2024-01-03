package com.blogging.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogging.app.entities.Post;
import com.blogging.app.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
     List<Post> getAllByUser(User user);
}
