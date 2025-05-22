package com.leverx.learningmanagementsystem.student.repository;

import com.leverx.learningmanagementsystem.student.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);

    @EntityGraph(attributePaths = {"courses"})
    List<Student> findAll();

    @EntityGraph(attributePaths = {"courses"})
    Optional<Student> findById(UUID id);
}
