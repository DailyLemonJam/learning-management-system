package com.leverx.learningmanagementsystem.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error Response DTO")
public record ErrorResponseDto(@Schema(description = "Message about error") String message) {
}
