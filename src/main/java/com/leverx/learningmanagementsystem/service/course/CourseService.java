package com.leverx.learningmanagementsystem.service.course;

import com.leverx.learningmanagementsystem.dto.course.CourseDto;
import com.leverx.learningmanagementsystem.dto.course.CreateCourseRequest;
import com.leverx.learningmanagementsystem.dto.course.UpdateCourseRequest;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    CourseDto create(CreateCourseRequest request);

    CourseDto get(UUID id);

    List<CourseDto> get();

    CourseDto update(UUID id, UpdateCourseRequest request);

    void delete(UUID id);

}
