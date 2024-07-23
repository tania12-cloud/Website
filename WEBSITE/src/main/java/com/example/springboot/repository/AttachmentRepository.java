package com.example.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.model.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
