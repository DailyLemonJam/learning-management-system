package com.leverx.learningmanagementsystem.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Update Lesson Request DTO")
public record UpdateLessonRequestDto(@Schema(description = "Updates Lesson title") @NotBlank @Size(min = 3, max = 255) String title,
                                     @Schema(description = "Updates Lessons duration") @Positive Integer duration) {
}
