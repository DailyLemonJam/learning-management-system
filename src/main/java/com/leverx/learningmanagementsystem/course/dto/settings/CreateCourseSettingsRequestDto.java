package com.leverx.learningmanagementsystem.course.dto.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Create CourseSettings Request DTO")
public record CreateCourseSettingsRequestDto(

        @Schema(description = "Sets start date of a Course")
        @NotNull @FutureOrPresent LocalDateTime startDate,

        @Schema(description = "Sets end date of a Course")
        @NotNull @FutureOrPresent LocalDateTime endDate,

        @Schema(description = "Sets if Course should be public")
        @NotNull Boolean isPublic

) {
}
