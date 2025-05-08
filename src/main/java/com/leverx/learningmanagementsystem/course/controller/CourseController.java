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

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
        var result = courseService.create(course);
        return courseMapper.toDto(result);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Get Course", description = "Returns Course information")
    public CourseResponseDto get(@PathVariable UUID id) {
        return courseMapper.toDto(courseService.get(id));
    }

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Get Courses", description = "Returns all Courses")
    public List<CourseResponseDto> get() {
        return courseMapper.toDtos(courseService.get());
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update Course", description = "Updates Course information without CourseSettings")
    public CourseResponseDto update(@PathVariable UUID id,
                                    @Valid @RequestBody UpdateCourseRequestDto request) {
        var course = courseMapper.toModel(request);
        var result = courseService.update(id, course);
        return courseMapper.toDto(result);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete Course", description = "Deletes Course entity from DB with associated Lessons")
    public void delete(@PathVariable UUID id) {
        courseService.delete(id);
    }

}
