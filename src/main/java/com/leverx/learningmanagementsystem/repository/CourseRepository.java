package com.leverx.learningmanagementsystem.repository;

import com.leverx.learningmanagementsystem.model.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findAllByCourseSettings_StartDateBetween(LocalDateTime tomorrow, LocalDateTime afterTomorrow);

    @EntityGraph(attributePaths = {"lessons"})
    List<Course> findAll();

    @EntityGraph(attributePaths = {"lessons"})
    Optional<Course> findById(UUID id);
}
