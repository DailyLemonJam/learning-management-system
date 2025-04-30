package com.leverx.learningmanagementsystem.dto.lesson;

import java.util.UUID;

public record CreateLessonRequest(String title,
                                  Integer duration,
                                  UUID courseId) {
}
