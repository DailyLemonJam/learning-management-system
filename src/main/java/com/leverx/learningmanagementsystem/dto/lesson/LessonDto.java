package com.leverx.learningmanagementsystem.dto.lesson;

import java.util.UUID;

public record LessonDto(UUID id,
                        String title,
                        Integer duration,
                        UUID courseId) {
}
