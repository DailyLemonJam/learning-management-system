package com.leverx.learningmanagementsystem.dto.course.settings;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateCourseSettingsRequest(@NotNull @FutureOrPresent LocalDateTime newStartDate,
                                          @NotNull @FutureOrPresent LocalDateTime newEndDate,
                                          @NotNull Boolean newIsPublic) {
}
