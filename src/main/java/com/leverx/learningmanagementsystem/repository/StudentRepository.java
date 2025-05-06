package com.leverx.learningmanagementsystem.repository;

import com.leverx.learningmanagementsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByEmail(String email);
}
