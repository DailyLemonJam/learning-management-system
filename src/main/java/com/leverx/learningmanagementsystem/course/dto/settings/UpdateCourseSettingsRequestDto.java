package com.leverx.learningmanagementsystem.course.dto.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Update CourseSettings Request DTO")
public record UpdateCourseSettingsRequestDto(

        @Schema(description = "Updates start date of a Course")
        @NotNull @FutureOrPresent LocalDateTime startDate,

        @Schema(description = "Updates end date of a Course")
        @NotNull @FutureOrPresent LocalDateTime endDate,

        @Schema(description = "Updates if Course should be public")
        @NotNull Boolean isPublic

) {
}
