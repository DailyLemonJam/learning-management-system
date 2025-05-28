package com.leverx.learningmanagementsystem.course.dto;

import com.leverx.learningmanagementsystem.course.dto.settings.CourseSettingsResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "Course Response DTO")
public record CourseResponseDto(@Schema(description = "Course UUID") @NotNull UUID id,
                                @Schema(description = "Course title") @NotBlank @Size(min = 3, max = 255) String title,
                                @Schema(description = "Course description") @NotBlank @Size(min = 3, max = 255) String description,
                                @Schema(description = "Course price") @NotNull BigDecimal price,
                                @Schema(description = "Associated CourseSettings") @NotNull CourseSettingsResponseDto courseSettings,
                                @Schema(description = "Associated Lessons UUID") @NotNull List<UUID> lessons) {
}
