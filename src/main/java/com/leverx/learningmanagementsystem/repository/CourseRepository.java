package com.leverx.learningmanagementsystem.repository;

import com.leverx.learningmanagementsystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findAllByCourseSettings_StartDate(LocalDateTime courseSettings_startDate);
}
