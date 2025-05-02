package com.leverx.learningmanagementsystem.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateCourseRequest(@NotBlank @Size(min = 3, max = 255) String newTitle,
                                  @NotBlank @Size(min = 3, max = 255) String newDescription,
                                  @NotNull BigDecimal newPrice) {
}
