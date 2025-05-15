package com.leverx.learningmanagementsystem.service.course;

import com.leverx.learningmanagementsystem.model.Course;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Course create(Course course);

    Course get(UUID id);

    List<Course> get();

    Course update(UUID id, Course course);

    void delete(UUID id);

}
