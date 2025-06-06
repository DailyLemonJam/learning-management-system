package com.leverx.learningmanagementsystem.course.controller;

import com.leverx.learningmanagementsystem.AbstractCommonIT;
import com.leverx.learningmanagementsystem.course.dto.CreateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.UpdateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.settings.CreateCourseSettingsRequestDto;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.util.CourseUtilIT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CourseControllerIT extends AbstractCommonIT {

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    protected void setUp() {
        courseRepository.deleteAll();
    }

    @AfterEach
    protected void tearDown() {
        courseRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void createCourse_givenCreateCourseRequestDto_shouldReturnCourseAndReturn201() throws Exception {
        // given
        var createCourseSettingsRequestDto = new CreateCourseSettingsRequestDto(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                true
        );
        var createCourseRequestDto = new CreateCourseRequestDto("New Course", "Description for course",
                BigDecimal.valueOf(100), createCourseSettingsRequestDto);

        // when
        var result = mockMvc.perform(post("/courses")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseRequestDto)));
        var getResult = mockMvc.perform(get("/courses"));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.title").value("New Course"));
        result.andExpect(jsonPath("$.description").value("Description for course"));
        result.andExpect(jsonPath("$.courseSettings.isPublic").value(true));

        getResult.andExpect(status().isOk());
        getResult.andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    @WithMockUser
    public void getCourse_givenId_shouldReturnCourseAndReturn200() throws Exception {
        // given
        var course = CourseUtilIT.createCourse();
        var savedCourse = courseRepository.save(course);

        // when
        var result = mockMvc.perform(get("/courses/{id}", savedCourse.getId()));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.title").value("Java Course"));
        result.andExpect(jsonPath("$.description").value("The most useful description"));
        result.andExpect(jsonPath("$.price").value(BigDecimal.valueOf(50)));
    }

    @Test
    @WithMockUser
    public void getAllCourses_shouldReturnAllCoursesAndReturn200() throws Exception {
        // given
        var course = CourseUtilIT.createCourse();
        var course2 = CourseUtilIT.createCourse();
        course2.setPrice(BigDecimal.valueOf(100));
        courseRepository.save(course);
        courseRepository.save(course2);

        // when
        var result = mockMvc.perform(get("/courses")
                .param("sort", "price,asc"));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content.length()").value(2));
        result.andExpect(jsonPath("$.content[0].title").value("Java Course"));
        result.andExpect(jsonPath("$.content[1].price").value(BigDecimal.valueOf(100)));
    }

    @Test
    @WithMockUser
    public void updateCourse_givenUpdateCourseRequestDto_shouldUpdateCourseAndReturn200() throws Exception {
        // given
        var course = CourseUtilIT.createCourse();
        var savedCourse = courseRepository.save(course);

        var updateRequest = new UpdateCourseRequestDto("New Nice Course Title", "New description", BigDecimal.valueOf(60));

        // when
        var result = mockMvc.perform(put("/courses/{id}", savedCourse.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.title").value("New Nice Course Title"));
        result.andExpect(jsonPath("$.description").value("New description"));
        result.andExpect(jsonPath("$.price").value(BigDecimal.valueOf(60)));
        result.andExpect(jsonPath("$.id").value(savedCourse.getId().toString()));
    }

    @Test
    @WithMockUser
    public void deleteCourse_givenId_shouldDeleteCourseAndReturn204AndReturn404() throws Exception {
        // given
        var course = CourseUtilIT.createCourse();
        var savedCourse = courseRepository.save(course);

        // when
        var result = mockMvc.perform(delete("/courses/{id}", savedCourse.getId())
                .with(csrf()));
        var deleteResult = mockMvc.perform(delete("/courses/{id}", savedCourse.getId())
                .with(csrf()));

        // then
        result.andExpect(status().isNoContent());
        deleteResult.andExpect(status().isNotFound());
    }
}
