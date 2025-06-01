package com.leverx.learningmanagementsystem.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Locale;

@Schema(description = "Create Student Request DTO")
public record CreateStudentRequestDto(

        @Schema(description = "Sets Student firstName")
        @NotBlank @Size(min = 2, max = 50) String firstName,

        @Schema(description = "Sets Student lastName")
        @NotBlank @Size(min = 2, max = 50) String lastName,

        @Schema(description = "Sets Student email")
        @NotBlank @Email String email,

        @Schema(description = "Sets Student dateOfBirth")
        @NotNull @Past LocalDate dateOfBirth,

        @Schema(description = "Locale of Student")
        Locale locale

) {
}
