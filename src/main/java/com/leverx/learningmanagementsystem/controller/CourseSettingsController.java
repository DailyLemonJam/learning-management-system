package com.leverx.learningmanagementsystem.controller;

import com.leverx.learningmanagementsystem.dto.course.settings.CourseSettingsDto;
import com.leverx.learningmanagementsystem.dto.course.settings.UpdateCourseSettingsRequest;
import com.leverx.learningmanagementsystem.service.course.settings.CourseSettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/course-settings")
@RequiredArgsConstructor
public class CourseSettingsController {
    private final CourseSettingsService courseSettingsService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseSettingsDto getCourseSettings(@PathVariable UUID id) {
        return courseSettingsService.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseSettingsDto updateCourseSettings(@PathVariable UUID id,
                                                  @Valid @RequestBody UpdateCourseSettingsRequest request) {
        return courseSettingsService.update(id, request);
    }

}
