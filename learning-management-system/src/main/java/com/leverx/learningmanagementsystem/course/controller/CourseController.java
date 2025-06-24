package com.leverx.learningmanagementsystem.course.controller;

import com.leverx.learningmanagementsystem.course.dto.CourseResponseDto;
import com.leverx.learningmanagementsystem.course.dto.CreateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.UpdateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.mapper.CourseMapper;
import com.leverx.learningmanagementsystem.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Course Controller", description = "Handles Course operations")
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create Course", description = "Creates new Course entity with associated CourseSettings")
    public CourseResponseDto create(@Valid @RequestBody CreateCourseRequestDto request) {
        var course = courseMapper.toModel(request);
        var createdCourse = courseService.create(course);
        return courseMapper.toDto(createdCourse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Get Course", description = "Returns Course information")
    public CourseResponseDto get(@PathVariable UUID id) {
        var course = courseService.get(id);
        return courseMapper.toDto(course);
    }

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Get Courses", description = "Returns all Courses")
    public PagedModel<CourseResponseDto> getAll(Pageable pageable) {
        var courses = courseService.getAll(pageable);
        return courseMapper.toDtos(courses);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update Course", description = "Updates Course information without CourseSettings")
    public CourseResponseDto update(@PathVariable UUID id,
                                    @Valid @RequestBody UpdateCourseRequestDto request) {
        var course = courseMapper.toModel(request);
        var updatedCourse = courseService.update(id, course);
        return courseMapper.toDto(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete Course", description = "Deletes Course entity from DB with associated Lessons")
    public void delete(@PathVariable UUID id) {
        courseService.delete(id);
    }
}
