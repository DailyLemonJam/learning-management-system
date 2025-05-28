package com.leverx.learningmanagementsystem.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Update Student Request DTO")
public record UpdateStudentRequestDto(@Schema(description = "Updates Student firstName") @NotBlank @Size(min = 2, max = 50) String firstName,
                                      @Schema(description = "Updates Student lastName") @NotBlank @Size(min = 2, max = 50) String lastName,
                                      @Schema(description = "Updates Student email") @NotBlank @Email String email,
                                      @Schema(description = "Updates Student dateOfBirth") @NotNull @Past LocalDate dateOfBirth) {
}
