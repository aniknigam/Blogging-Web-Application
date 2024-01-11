package com.blogging.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogging.app.entities.User;

public interface UserRepo extends JpaRepository<User,Integer> {
	Optional<User> findByName(String username);
}
