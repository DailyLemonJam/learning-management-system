package com.leverx.learningmanagementsystem.dto.course;

import com.leverx.learningmanagementsystem.dto.course.settings.CreateCourseSettingsRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateCourseRequest(@NotBlank @Size(min = 3, max = 255) String title,
                                  @NotBlank @Size(min = 3, max = 255) String description,
                                  @NotNull BigDecimal price,
                                  @NotNull CreateCourseSettingsRequest createCourseSettingsRequest) {
}
