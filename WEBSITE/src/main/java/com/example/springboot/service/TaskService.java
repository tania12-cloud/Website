package com.example.springboot.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.springboot.model.Attachment;
import com.example.springboot.model.Comment;
import com.example.springboot.model.Task;
import com.example.springboot.model.TaskStatus;
import com.example.springboot.model.User;
import com.example.springboot.repository.AttachmentRepository;
import com.example.springboot.repository.CommentRepository;
import com.example.springboot.repository.TaskRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
	
	private static final String UPLOAD_DIR = "uploads/";

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
    private AttachmentRepository attachmentRepository;
	@Autowired
    private UserService userService;
	private EntityManager entityManager;

	public TaskService(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public List<Task> findTasksByAssignee(String username) {
		return taskRepository.findByAssigneeUsername(username);
	}

	@Transactional
	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	public Task findTaskById(Long id) {

		Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
		task.getAssignee().getUsername();
		return task;
	}

	@SuppressWarnings("deprecation")
	@Transactional
	public void updateTask(Long id, String comment, TaskStatus status) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.createNativeQuery("UPDATE task SET comments=:comment WHERE  id=:id")
				.setParameter("comment", comment).setParameter("id", id).executeUpdate();
	}

	@Transactional
	public Comment addComment(Long taskId, Comment comment) {
		Task task = findTaskById(taskId);
		comment.setTask(task);
		comment.setCreatedDateTime(LocalDateTime.now());
		return commentRepository.save(comment);
	}
	
	@Transactional
    public Attachment addAttachment(Long taskId, MultipartFile file, User user) throws IOException {
        Task task = findTaskById(taskId);

        if (!isValidFileType(file)) {
            throw new IllegalArgumentException("Invalid file type. Only .txt, .jpg, and .pdf are allowed.");
        }

        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        attachment.setFileType(fileType);
        attachment.setFilePath(filePath.toString());
        attachment.setCreatedDateTime(LocalDateTime.now());
        attachment.setUser(user);
        attachment.setTask(task);

        return attachmentRepository.save(attachment);
    }

    private boolean isValidFileType(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("text/plain") || contentType.equals("image/jpeg") || contentType.equals("application/pdf"));
    }
    
    public void reassignTask(Long taskId, String newAssigneeUsername) {
        Task task = findTaskById(taskId);
        User newAssignee = userService.findByUsername(newAssigneeUsername).orElseThrow(() -> new RuntimeException("User not found"));
        task.setAssignee(newAssignee);
        taskRepository.save(task);
    }
   
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
        
    }
    
}
