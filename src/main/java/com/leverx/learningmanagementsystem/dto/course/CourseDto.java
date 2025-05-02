package com.leverx.learningmanagementsystem.dto.course;

import com.leverx.learningmanagementsystem.dto.course.settings.CourseSettingsDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CourseDto(@NotNull UUID id,
                        @NotBlank @Size(min = 3, max = 255) String title,
                        @NotBlank @Size(min = 3, max = 255) String description,
                        @NotNull BigDecimal price,
                        @NotNull CourseSettingsDto courseSettings,
                        @NotNull List<UUID> lessons) {
}
