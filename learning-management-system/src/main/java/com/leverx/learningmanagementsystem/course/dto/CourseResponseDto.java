package com.leverx.learningmanagementsystem.course.dto;

import com.leverx.learningmanagementsystem.course.dto.settings.CourseSettingsResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "Course Response DTO")
public record CourseResponseDto(

        @Schema(description = "Course UUID")
        UUID id,

        @Schema(description = "Course title")
        String title,

        @Schema(description = "Course description")
        String description,

        @Schema(description = "Course price")
        BigDecimal price,

        @Schema(description = "Associated CourseSettings")
        CourseSettingsResponseDto courseSettings,

        @Schema(description = "Associated Lessons UUID")
        List<UUID> lessons
) {
}
