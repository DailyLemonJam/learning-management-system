package com.leverx.learningmanagementsystem.student.repository;

import com.leverx.learningmanagementsystem.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    @EntityGraph(attributePaths = {"courses"})
    Optional<Student> findByEmail(String email);

    @EntityGraph(attributePaths = {"courses"})
    Page<Student> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"courses"})
    Optional<Student> findById(UUID id);

}
