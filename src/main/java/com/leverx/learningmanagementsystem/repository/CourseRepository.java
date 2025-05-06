package com.leverx.learningmanagementsystem.repository;

import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.model.CourseSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findAllByCourseSettings_StartDateBetween(LocalDateTime tomorrow, LocalDateTime afterTomorrow);
}
