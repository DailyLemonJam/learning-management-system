package com.leverx.learningmanagementsystem.util;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.student.model.Student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class StudentUtil {

    public static Student createStudent(List<Course> enrolledCourses) {
        return Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email(UUID.randomUUID() + "john.doe@example.com")
                .dateOfBirth(LocalDate.of(2000, 5, 8))
                .coins(BigDecimal.valueOf(50))
                .courses(enrolledCourses)
                .build();
    }
}
