package com.leverx.learningmanagementsystem.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Update Lesson Request DTO")
public record UpdateLessonRequestDto(

        @Schema(description = "Updates Lesson title")
        @NotBlank @Size(min = 3, max = 255) String title,

        @Schema(description = "Updates Lessons duration")
        @Positive Integer duration,

        @Schema(description = "Lesson type: Classroom or Video")
        @NotNull String lessonType,

        @Schema(description = "Physical location (for classroom)")
        @Nullable String location,

        @Schema(description = "Max people allowed (for classroom)")
        @Nullable @Positive Integer capacity,

        @Schema(description = "Url for joining (for video)")
        @Nullable String url,

        @Schema(description = "Platform where lesson is held (for video)")
        @Nullable String platform
) {
}
