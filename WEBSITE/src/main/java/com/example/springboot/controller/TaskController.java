package com.example.springboot.controller;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.springboot.model.Comment;
import com.example.springboot.model.Task;
import com.example.springboot.model.TaskStatus;
import com.example.springboot.model.User;
import com.example.springboot.service.TaskService;
import com.example.springboot.service.UserService;

@Controller
public class TaskController {
	
	private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewTasks(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("tasks", taskService.findTasksByAssignee(username));
        return "index";
    }

    @GetMapping("/tasks/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", userService.findAllUsers());
        return "task_form";
    }

    @PostMapping("/tasks")
    public String createTask(@ModelAttribute Task task, Authentication authentication) {
        String username = authentication.getName();
        //User assignee = userService.findByUsername(username).orElseThrow();
        //task.setAssignee(assignee);
        task.setStatus(TaskStatus.OPEN);
        System.out.println("TASK:------"+task);
        taskService.saveTask(task);
        return "redirect:/";
    }
    
    @GetMapping("/tasks/{id}")
    @Transactional
    public String viewTaskDetail(@PathVariable Long id, Model model) {
        Task task = taskService.findTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("users", userService.findAllUsers());
        return "task_detail";
    }

    @PostMapping("/tasks/{id}/update")
    @Transactional
    public String updateTask(@PathVariable Long id, @RequestParam TaskStatus status) {
        Task task = taskService.findTaskById(id);
        task.setStatus(status);
        taskService.saveTask(task);
        return "redirect:/tasks/" + id;
    }
    
    @PostMapping("/tasks/{id}/comments")
    public String addComment(@PathVariable Long id, @RequestParam String commentText, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow();
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setUser(user);
        taskService.addComment(id, comment);
        return "redirect:/tasks/" + id;
    }
    
    @PostMapping("/tasks/{id}/attachments")
    public String addAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username).orElseThrow();
            taskService.addAttachment(id, file, user);
        } catch (IOException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/tasks/" + id;
    }
    
    @PostMapping("/tasks/{id}/reassign")
    public String reassignTask(@PathVariable Long id, @RequestParam String newAssignee, RedirectAttributes redirectAttributes) {
        try {
            taskService.reassignTask(id, newAssignee);
            redirectAttributes.addFlashAttribute("message", "Task reassigned successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/attachments/{filename}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        Resource resource = new UrlResource(filePath.toUri());
        System.out.println(filePath);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
    
    @GetMapping("/error")
    public String showErrorPage(Model model) {
        return "error";
    }
}
