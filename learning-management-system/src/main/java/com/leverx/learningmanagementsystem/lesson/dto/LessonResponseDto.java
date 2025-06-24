package com.leverx.learningmanagementsystem.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Lesson Response DTO")
public record LessonResponseDto(

        @Schema(description = "Lesson UUID")
        UUID id,

        @Schema(description = "Lesson title")
        String title,

        @Schema(description = "Lesson duration")
        Integer duration,

        @Schema(description = "Lesson type: Classroom or Video")
        String lessonType,

        @Schema(description = "Physical location (for classroom)")
        String location,

        @Schema(description = "Max people allowed (for classroom)")
        Integer capacity,

        @Schema(description = "Url for joining (for video)")
        String url,

        @Schema(description = "Platform where lesson is held (for video)")
        String platform,

        @Schema(description = "Associated Course UUID")
        UUID courseId
) {
}
