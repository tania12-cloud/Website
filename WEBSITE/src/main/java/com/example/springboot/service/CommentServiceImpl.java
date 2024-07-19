package com.example.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.model.Comment;
import com.example.springboot.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment updateComment(Long id, Comment commentDetails) {
        return commentRepository.findById(id).map(comment -> {
            comment.setText(commentDetails.getText());
            comment.setCreatedDateTime(commentDetails.getCreatedDateTime());
            comment.setUser(commentDetails.getUser());
            comment.setTask(commentDetails.getTask());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

	
}






