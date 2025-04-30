package com.leverx.learningmanagementsystem.dto.student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record StudentDto(UUID id,
                         String firstName,
                         String lastName,
                         String email,
                         LocalDate dateOfBirth,
                         BigDecimal coins,
                         List<UUID> courses) {
}
