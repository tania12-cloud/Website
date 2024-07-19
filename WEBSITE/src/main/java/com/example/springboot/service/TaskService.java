package com.example.springboot.service;

import java.util.List;
import java.util.Optional;

import com.example.springboot.model.Task;

public interface TaskService {
	Task createTask(Task task);
	Optional<Task> getTaskById(Long id);
	List<Task> getAllTasks();
	Task updateTask(Long id, Task taskDetails);
	void deleteTask(Long id);

}
