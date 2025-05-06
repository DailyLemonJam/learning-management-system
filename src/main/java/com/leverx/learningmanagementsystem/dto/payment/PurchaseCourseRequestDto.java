package com.leverx.learningmanagementsystem.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Purchase Course Request DTO")
public record PurchaseCourseRequestDto(@Schema(description = "Connects Course and Student after successful purchase") @NotNull UUID studentId) {
}
