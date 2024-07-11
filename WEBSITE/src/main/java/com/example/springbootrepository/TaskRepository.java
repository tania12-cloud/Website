package com.example.springbootrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.springbootmodel.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}
