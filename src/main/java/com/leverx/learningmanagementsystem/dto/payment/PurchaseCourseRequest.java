package com.leverx.learningmanagementsystem.dto.payment;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PurchaseCourseRequest(@NotNull UUID studentId) {
}
