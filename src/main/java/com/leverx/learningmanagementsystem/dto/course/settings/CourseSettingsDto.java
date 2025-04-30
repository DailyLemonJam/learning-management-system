package com.leverx.learningmanagementsystem.dto.course.settings;

import java.time.LocalDateTime;
import java.util.UUID;

public record CourseSettingsDto(UUID id,
                                LocalDateTime startDate,
                                LocalDateTime endDate,
                                Boolean isPublic) {
}
