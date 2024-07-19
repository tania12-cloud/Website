package com.example.springboot.yController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.model.Comment;
import com.example.springboot.model.Task;
import com.example.springboot.model.User;
import com.example.springboot.service.CommentService;
import com.example.springboot.service.TaskService;
import com.example.springboot.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listComments(Model model) {
        List<Comment> comments = commentService.getAllComments();
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/create")
    public String showCreateCommentForm(Model model) {
        model.addAttribute("comment", new Comment());
        List<Task> tasks = taskService.getAllTasks();
        List<User> users = userService.getAllUsers();
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", users);
        return "create_comment";
    }

    @PostMapping("/create")
    public String createComment(@ModelAttribute Comment comment) {
        commentService.createComment(comment);
        return "redirect:/comments";
    }

    @GetMapping("/{id}")
    public String getCommentById(@PathVariable Long id, Model model) {
        Optional<Comment> comment = commentService.getCommentById(id);
        
        	model.addAttribute("comment", comment);
        	return "comment";
        
        
    }

    @PostMapping("/update/{id}")
    public String updateComment(@PathVariable Long id, @ModelAttribute Comment commentDetails) {
        commentService.updateComment(id, commentDetails);
        return "redirect:/comments";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "redirect:/comments";
    }
}


