package com.leverx.learningmanagementsystem.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Create Student Request DTO")
public record CreateStudentRequestDto(@Schema(description = "Sets Student firstName") @NotBlank @Size(min = 2, max = 50) String firstName,
                                      @Schema(description = "Sets Student lastName") @NotBlank @Size(min = 2, max = 50) String lastName,
                                      @Schema(description = "Sets Student email") @NotBlank @Email String email,
                                      @Schema(description = "Sets Student dateOfBirth") @NotNull @Past LocalDate dateOfBirth) {
}
