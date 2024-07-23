package com.example.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboot.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	 Optional<User> findByUsername(String username);

}
