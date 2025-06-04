package com.leverx.learningmanagementsystem.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Create Lesson Request DTO")
public record CreateLessonRequestDto(

        @Schema(description = "Sets Lesson title")
        @NotBlank @Size(min = 3, max = 255) String title,

        @Schema(description = "Sets Lesson duration")
        @NotNull @Positive Integer duration,

        @Schema(description = "Lesson type: Classroom or Video")
        @NotNull String lessonType,

        @Schema(description = "Physical location (for classroom)")
        @Nullable String location,

        @Schema(description = "Max people allowed (for classroom)")
        @Nullable @Positive Integer capacity,

        @Schema(description = "Url for joining (for video)")
        @Nullable String url,

        @Schema(description = "Platform where lesson is held (for video)")
        @Nullable String platform,

        @Schema(description = "Sets associated Course UUID")
        @NotNull UUID courseId
) {
}
