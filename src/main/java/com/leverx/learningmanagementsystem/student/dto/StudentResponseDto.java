package com.leverx.learningmanagementsystem.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Schema(description = "Student Response DTO")
public record StudentResponseDto(

        @Schema(description = "Student UUID")
        UUID id,

        @Schema(description = "Student firstName")
        String firstName,

        @Schema(description = "Student lastName")
        String lastName,

        @Schema(description = "Student email")
        String email,

        @Schema(description = "Student dateOfBirth")
        LocalDate dateOfBirth,

        @Schema(description = "Student coins")
        BigDecimal coins,

        @Schema(description = "Locale of student")
        Locale locale,

        @Schema(description = "Enrolled courses")
        List<UUID> courses
) {
}
