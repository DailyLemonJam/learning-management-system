package com.leverx.learningmanagementsystem.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Schema(description = "Student Response DTO")
public record StudentResponseDto(@Schema(description = "Student UUID") @NotNull UUID id,

                                 @Schema(description = "Student firstName")
                                 @NotBlank @Size(min = 2, max = 50) String firstName,

                                 @Schema(description = "Student lastName")
                                 @NotBlank @Size(min = 2, max = 50) String lastName,

                                 @Schema(description = "Student email")
                                 @NotBlank @Email String email,

                                 @Schema(description = "Student dateOfBirth")
                                 @NotNull @Past LocalDate dateOfBirth,

                                 @Schema(description = "Student coins")
                                 @NotNull BigDecimal coins,

                                 @Schema(description = "Locale of student")
                                 Locale locale,

                                 @Schema(description = "Enrolled courses")
                                 @NotNull List<UUID> courses
) {
}
