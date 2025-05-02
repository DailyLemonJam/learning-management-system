package com.leverx.learningmanagementsystem.dto.student;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record StudentDto(@NotNull UUID id,
                         @NotBlank @Size(min = 2, max = 50) String firstName,
                         @NotBlank @Size(min = 2, max = 50) String lastName,
                         @NotBlank @Email String email,
                         @NotNull @Past LocalDate dateOfBirth,
                         @NotNull BigDecimal coins,
                         @NotNull List<UUID> courses) {
}
