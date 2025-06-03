package com.leverx.learningmanagementsystem.course.controller;

import com.leverx.learningmanagementsystem.course.dto.settings.CourseSettingsResponseDto;
import com.leverx.learningmanagementsystem.course.dto.settings.UpdateCourseSettingsRequestDto;
import com.leverx.learningmanagementsystem.course.mapper.CourseSettingsMapper;
import com.leverx.learningmanagementsystem.course.service.settings.CourseSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/course-settings")
@RequiredArgsConstructor
@Tag(name = "CourseSettings Controller", description = "Handles CourseSettings operations. Only GET and PUT operations available")
public class CourseSettingsController {

    private final CourseSettingsService courseSettingsService;
    private final CourseSettingsMapper courseSettingsMapper;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Get CourseSettings", description = "Returns CourseSettings information")
    public CourseSettingsResponseDto get(@PathVariable UUID id) {
        var settings = courseSettingsService.get(id);
        return courseSettingsMapper.toDto(settings);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update CourseSettings", description = "Updates CourseSettings information")
    public CourseSettingsResponseDto update(@PathVariable UUID id,
                                            @Valid @RequestBody UpdateCourseSettingsRequestDto request) {
        var settings = courseSettingsMapper.toModel(request);
        var updatedSettings = courseSettingsService.update(id, settings);
        return courseSettingsMapper.toDto(updatedSettings);
    }
}
