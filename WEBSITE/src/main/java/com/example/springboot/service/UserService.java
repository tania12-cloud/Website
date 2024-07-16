package com.example.springboot.service;

import java.util.List;
import java.util.Optional;

import com.example.springboot.model.User;

public interface UserService {
	User createUser(User user);
	Optional<User> getUserById(Long id);
	List<User> getAllUsers();
	User updateUser(Long id, User userDetails);
	void deleteUser(Long id);
	

}
