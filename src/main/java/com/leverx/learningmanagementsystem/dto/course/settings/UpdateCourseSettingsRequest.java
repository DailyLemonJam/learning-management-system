package com.leverx.learningmanagementsystem.dto.course.settings;

import java.time.LocalDateTime;

public record UpdateCourseSettingsRequest(LocalDateTime newStartDate,
                                          LocalDateTime newEndDate,
                                          Boolean newIsPublic) {
}
