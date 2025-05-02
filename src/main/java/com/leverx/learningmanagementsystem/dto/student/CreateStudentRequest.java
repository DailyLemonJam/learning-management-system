package com.leverx.learningmanagementsystem.dto.student;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateStudentRequest(@NotBlank @Size(min = 2, max = 50) String firstName,
                                   @NotBlank @Size(min = 2, max = 50) String lastName,
                                   @NotBlank @Email String email,
                                   @NotNull @Past LocalDate dateOfBirth) {
}
