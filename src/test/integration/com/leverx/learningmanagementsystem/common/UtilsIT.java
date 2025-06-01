package com.leverx.learningmanagementsystem.common;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import com.leverx.learningmanagementsystem.lesson.model.ClassroomLesson;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import com.leverx.learningmanagementsystem.lesson.model.VideoLesson;
import com.leverx.learningmanagementsystem.student.model.Student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UtilsIT {

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

    public static Course createCourse2() {
        var courseSettings2 = CourseSettings.builder()
                .startDate(LocalDateTime.now().plusDays(25))
                .endDate(LocalDateTime.now().plusDays(50))
                .isPublic(true)
                .build();
        var course2 = Course.builder()
                .title("Java Course 2")
                .courseSettings(courseSettings2)
                .description("The most useful description 2")
                .price(BigDecimal.valueOf(100))
                .coinsPaid(BigDecimal.valueOf(500))
                .build();
        course2.getCourseSettings().setCourse(course2);
        return course2;
    }

    public static Lesson createVideoLesson(Course course) {
        return VideoLesson.builder()
                .title("Test video lesson")
                .platform("google")
                .duration(90)
                .course(course)
                .build();
    }

    public static Lesson createClassroomLesson(Course course) {
        return ClassroomLesson.builder()
                .title("Test classroom lesson")
                .capacity(30)
                .location("London")
                .duration(120)
                .course(course)
                .build();
    }

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
