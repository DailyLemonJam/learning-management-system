package com.leverx.learningmanagementsystem.course.service;

import com.leverx.learningmanagementsystem.course.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseService {

    Course create(Course course);

    Course get(UUID id);

    Page<Course> getAll(Pageable pageable);

    Course update(UUID id, Course course);

    void delete(UUID id);
}
