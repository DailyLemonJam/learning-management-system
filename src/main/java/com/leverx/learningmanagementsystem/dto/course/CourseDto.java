package com.leverx.learningmanagementsystem.dto.course;

import com.leverx.learningmanagementsystem.dto.course.settings.CourseSettingsDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CourseDto(UUID id,
                        String title,
                        String description,
                        BigDecimal price,
                        CourseSettingsDto courseSettings,
                        List<UUID> lessons) {
}
