package com.leverx.learningmanagementsystem.course.service;

import com.leverx.learningmanagementsystem.course.model.Course;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Course create(Course course);

    Course get(UUID id);

    List<Course> getAll();

    Course update(UUID id, Course course);

    void delete(UUID id);

}
