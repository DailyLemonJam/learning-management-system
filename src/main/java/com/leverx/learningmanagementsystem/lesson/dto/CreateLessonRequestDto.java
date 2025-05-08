package com.leverx.learningmanagementsystem.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Create Lesson Request DTO")
public record CreateLessonRequestDto(@Schema(description = "Sets Lesson title") @NotBlank @Size(min = 3, max = 255) String title,
                                     @Schema(description = "Sets Lesson duration") @NotNull @Positive Integer duration,
                                     @Schema(description = "Sets associated Course UUID") @NotNull UUID courseId) {
}
