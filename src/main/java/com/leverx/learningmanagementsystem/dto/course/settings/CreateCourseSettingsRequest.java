package com.leverx.learningmanagementsystem.dto.course.settings;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateCourseSettingsRequest(@NotNull @FutureOrPresent LocalDateTime startDate,
                                          @NotNull @FutureOrPresent LocalDateTime endDate,
                                          @NotNull Boolean isPublic) {
}
