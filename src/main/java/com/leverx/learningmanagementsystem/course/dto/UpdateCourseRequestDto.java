package com.leverx.learningmanagementsystem.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Update Course Request DTO")
public record UpdateCourseRequestDto(

        @Schema(description = "Updates Course title")
        @NotBlank @Size(min = 3, max = 255) String title,

        @Schema(description = "Updates Course description")
        @NotBlank @Size(min = 3, max = 255) String description,

        @Schema(description = "Updates Course price")
        @NotNull BigDecimal price
) {
}
