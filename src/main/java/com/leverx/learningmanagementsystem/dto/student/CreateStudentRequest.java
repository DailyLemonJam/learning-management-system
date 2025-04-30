package com.leverx.learningmanagementsystem.dto.student;

import java.time.LocalDate;

public record CreateStudentRequest(String firstName,
                                   String lastName,
                                   String email,
                                   LocalDate dateOfBirth) {
}
