package com.example.springboot.service;



import java.util.List;
import java.util.Optional;

import com.example.springboot.model.Comment;

public interface CommentService {
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment commentDetails);
    Optional<Comment> getCommentById(Long id);
    List<Comment> getAllComments();
    void deleteComment(Long id);
    
}


