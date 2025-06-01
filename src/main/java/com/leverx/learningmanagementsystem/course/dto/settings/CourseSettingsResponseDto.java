package com.leverx.learningmanagementsystem.course.dto.settings;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "CourseSettings Response DTO")
public record CourseSettingsResponseDto(

        @Schema(description = "Settings UUID")
        UUID id,

        @Schema(description = "Start date of a Course")
        LocalDateTime startDate,

        @Schema(description = "End date of a Course")
        LocalDateTime endDate,

        @Schema(description = "Shows if Course is public")
        Boolean isPublic

) {
}
