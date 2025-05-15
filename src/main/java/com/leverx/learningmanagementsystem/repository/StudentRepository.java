package com.leverx.learningmanagementsystem.repository;

import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"courses"})
    List<Student> findAll();

    @EntityGraph(attributePaths = {"courses"})
    Optional<Student> findById(UUID id);
}
