package com.leverx.learningmanagementsystem.controller;

import com.leverx.learningmanagementsystem.service.course.CourseService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AllArgsConstructor
class CourseControllerTest {
    private MockMvc mockMvc;

    @Test
    public void createCourse_givenCreateCourseRequestDto_shouldCreateCourseAndReturn201() {
        // Arrange

        // Act

        // Assert

    }
}
