package com.leverx.learningmanagementsystem.dto.lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateLessonRequest(@NotBlank @Size(min = 3, max = 255) String newTitle,
                                  @Positive Integer newDuration) {
}
