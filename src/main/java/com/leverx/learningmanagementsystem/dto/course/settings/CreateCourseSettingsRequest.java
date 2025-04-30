package com.leverx.learningmanagementsystem.dto.course.settings;

import java.time.LocalDateTime;

public record CreateCourseSettingsRequest(LocalDateTime startDate,
                                          LocalDateTime endDate,
                                          Boolean isPublic) {
}
