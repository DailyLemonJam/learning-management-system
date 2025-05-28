package com.leverx.learningmanagementsystem.course.dto;

import com.leverx.learningmanagementsystem.course.dto.settings.CreateCourseSettingsRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Create Course Request DTO")
public record CreateCourseRequestDto(@Schema(description = "Sets Course title") @NotBlank @Size(min = 3, max = 255) String title,
                                     @Schema(description = "Sets Course description") @NotBlank @Size(min = 3, max = 255) String description,
                                     @Schema(description = "Sets Course price") @NotNull BigDecimal price,
                                     @Schema(description = "Sets CourseSettings") @NotNull CreateCourseSettingsRequestDto createCourseSettingsRequestDto) {
}
