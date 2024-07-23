package com.example.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.model.Task;


public interface TaskRepository extends JpaRepository<Task, Long>{
	List<Task> findByAssigneeUsername(String username);

}
