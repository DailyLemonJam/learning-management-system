package com.leverx.learningmanagementsystem.dto.lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LessonDto(@NotNull UUID id,
                        @NotBlank @Size(min = 3, max = 255) String title,
                        @NotNull @Positive Integer duration,
                        @NotNull UUID courseId) {
}
