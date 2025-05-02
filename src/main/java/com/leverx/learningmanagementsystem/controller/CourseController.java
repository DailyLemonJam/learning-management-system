package com.leverx.learningmanagementsystem.controller;

import com.leverx.learningmanagementsystem.dto.course.CourseDto;
import com.leverx.learningmanagementsystem.dto.course.CreateCourseRequest;
import com.leverx.learningmanagementsystem.dto.course.UpdateCourseRequest;
import com.leverx.learningmanagementsystem.service.course.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto createCourse(@Valid @RequestBody CreateCourseRequest request) {
        return courseService.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto getCourse(@PathVariable UUID id) {
        return courseService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDto> getCourses() {
        return courseService.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDto updateCourse(@PathVariable UUID id,
                                  @Valid @RequestBody UpdateCourseRequest request) {
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable UUID id) {
        courseService.delete(id);
    }

}
