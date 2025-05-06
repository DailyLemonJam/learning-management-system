package com.leverx.learningmanagementsystem.dto.course.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "CourseSettings Response DTO")
public record CourseSettingsResponseDto(@Schema(description = "Settings UUID") @NotNull UUID id,
                                        @Schema(description = "Start date of a Course") @NotNull LocalDateTime startDate,
                                        @Schema(description = "End date of a Course") @NotNull LocalDateTime endDate,
                                        @Schema(description = "Shows if Course is public") @NotNull Boolean isPublic) {
}
