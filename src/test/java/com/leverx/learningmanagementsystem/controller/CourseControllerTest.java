package com.leverx.learningmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.learningmanagementsystem.course.dto.CreateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.UpdateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.settings.CreateCourseSettingsRequestDto;
import com.leverx.learningmanagementsystem.security.role.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${security.configuration.default-user.username}")
    private String defaultUserUsername;

    @Value("${security.configuration.default-user.password}")
    private String defaultUserPassword;

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void createCourse_givenCreateCourseRequestDto_shouldCreateCourseAndReturn201() throws Exception {
        // given
        var settings = new CreateCourseSettingsRequestDto(LocalDateTime.now(), LocalDateTime.now().plusDays(1), true);
        var request = new CreateCourseRequestDto("title", "description",
                new BigDecimal(250), settings);

        // when
        var result = mockMvc.perform(post("/courses")
                        .with(csrf())
                        .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.title").value("title"));
        result.andExpect(jsonPath("$.description").value("description"));
        result.andExpect(jsonPath("$.price").value(new BigDecimal(250)));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getCourse_givenId_shouldReturnCourseAndReturn200() throws Exception {
        // when
        var result = mockMvc.perform(get("/courses/{id}", "b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d")
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.price").value(new BigDecimal(50)));
        result.andExpect(jsonPath("$.title").value("Java Course"));
        result.andExpect(jsonPath("$.description").value("The mose useful description"));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllCourses_shouldReturnAllCoursesAndReturn200() throws Exception {
        // when
        var result = mockMvc.perform(get("/courses")
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateCourse_givenUpdateCourseRequestDto_shouldUpdateCourseAndReturn200() throws Exception {
        // given
        var request = new UpdateCourseRequestDto("New Title", "New Description", BigDecimal.valueOf(20));

        // when
        var result = mockMvc.perform(put("/courses/{id}", "b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d")
                        .with(csrf())
                        .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.title").value("New Title"));
        result.andExpect(jsonPath("$.description").value("New Description"));
        result.andExpect(jsonPath("$.price").value(new BigDecimal(20)));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteCourse_givenId_shouldDeleteCourseAndReturn204AndReturn404() throws Exception {
        // when
        var result = mockMvc.perform(delete("/courses/{id}", "b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d")
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isNoContent());

        // when
        result = mockMvc.perform(delete("/courses/{id}", "b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d")
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isNotFound());
    }

}
