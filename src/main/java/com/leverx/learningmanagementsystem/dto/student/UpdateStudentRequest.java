package com.leverx.learningmanagementsystem.dto.student;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateStudentRequest(@NotBlank @Size(min = 2, max = 50) String newFirstName,
                                   @NotBlank @Size(min = 2, max = 50) String newLastName,
                                   @NotBlank @Email String newEmail,
                                   @NotNull @Past LocalDate newDateOfBirth) {
}
