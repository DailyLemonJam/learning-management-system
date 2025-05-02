package com.leverx.learningmanagementsystem.dto.course.settings;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CourseSettingsDto(@NotNull UUID id,
                                @NotNull LocalDateTime startDate,
                                @NotNull LocalDateTime endDate,
                                @NotNull Boolean isPublic) {
}
