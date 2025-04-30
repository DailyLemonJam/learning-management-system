package com.leverx.learningmanagementsystem.dto.lesson;

import java.util.UUID;

public record UpdateLessonRequest(String newTitle,
                                  Integer newDuration) {
}
