package com.leverx.learningmanagementsystem.dto.course;

import com.leverx.learningmanagementsystem.dto.course.settings.UpdateCourseSettingsRequest;

import java.math.BigDecimal;

public record UpdateCourseRequest(String newTitle,
                                  String newDescription,
                                  BigDecimal newPrice,
                                  UpdateCourseSettingsRequest newCourseSettings) {
}
