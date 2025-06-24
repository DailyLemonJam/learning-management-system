package com.leverx.learningmanagementsystem.util;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseUtilIT {

    public static Course createCourse() {
        var courseSettings = CourseSettings.builder()
                .startDate(LocalDateTime.now().plusDays(25))
                .endDate(LocalDateTime.now().plusDays(50))
                .isPublic(true)
                .build();
        var course = Course.builder()
                .title("Java Course")
                .courseSettings(courseSettings)
                .description("The most useful description")
                .price(BigDecimal.valueOf(50))
                .coinsPaid(BigDecimal.valueOf(250))
                .build();
        course.getCourseSettings().setCourse(course);
        return course;
    }
}
