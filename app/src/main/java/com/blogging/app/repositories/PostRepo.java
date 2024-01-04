package com.blogging.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blogging.app.entities.Category;
import com.blogging.app.entities.Post;
import com.blogging.app.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);
	Page<Post> findByUser(User user, Pageable pageable);

	List<Post> findByCategory(Category category);
	Page<Post> findByCategory(Category user, Pageable pageable);
}
