package com.example.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByTaskId(Long taskId);

}
