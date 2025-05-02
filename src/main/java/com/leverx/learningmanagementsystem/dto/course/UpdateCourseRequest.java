package com.leverx.learningmanagementsystem.dto.course;

import java.math.BigDecimal;

public record UpdateCourseRequest(String newTitle,
                                  String newDescription,
                                  BigDecimal newPrice) {
}
