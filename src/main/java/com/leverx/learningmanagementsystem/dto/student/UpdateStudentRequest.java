package com.leverx.learningmanagementsystem.dto.student;

import java.time.LocalDate;

public record UpdateStudentRequest(String newFirstName,
                                   String newLastName,
                                   String newEmail,
                                   LocalDate newDateOfBirth) {
}
