package com.leverx.learningmanagementsystem.controller;

import com.leverx.learningmanagementsystem.dto.course.settings.CourseSettingsResponseDto;
import com.leverx.learningmanagementsystem.dto.course.settings.UpdateCourseSettingsRequestDto;
import com.leverx.learningmanagementsystem.mapper.CourseSettingsMapper;
import com.leverx.learningmanagementsystem.service.course.settings.CourseSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return courseSettingsMapper.toDto(courseSettingsService.get(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update CourseSettings", description = "Updates CourseSettings information")
    public CourseSettingsResponseDto update(@PathVariable UUID id,
                                            @Valid @RequestBody UpdateCourseSettingsRequestDto request) {
        var settings = courseSettingsMapper.toModel(request);
        var result = courseSettingsService.update(id, settings);
        return courseSettingsMapper.toDto(result);
    }

}
