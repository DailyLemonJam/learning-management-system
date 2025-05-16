package com.leverx.learningmanagementsystem.course.repository;

import com.leverx.learningmanagementsystem.course.model.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findAllByCourseSettings_StartDateBetween(LocalDateTime tomorrow, LocalDateTime afterTomorrow);

    @EntityGraph(attributePaths = {"lessons"})
    List<Course> findAll();

    @Lock(PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"lessons"})
    Optional<Course> findById(UUID id);
}
