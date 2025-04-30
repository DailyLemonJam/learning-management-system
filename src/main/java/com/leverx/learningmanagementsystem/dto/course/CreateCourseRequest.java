package com.leverx.learningmanagementsystem.dto.course;

import com.leverx.learningmanagementsystem.dto.course.settings.CreateCourseSettingsRequest;

import java.math.BigDecimal;

public record CreateCourseRequest(String title,
                                  String description,
                                  BigDecimal price,
                                  CreateCourseSettingsRequest createCourseSettingsRequest) {
}
