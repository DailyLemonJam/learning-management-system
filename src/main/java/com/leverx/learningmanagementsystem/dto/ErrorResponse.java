package com.leverx.learningmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;

public record ErrorResponse(@NotBlank String message) {
}
