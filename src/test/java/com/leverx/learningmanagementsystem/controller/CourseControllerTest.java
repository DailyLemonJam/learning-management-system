package com.leverx.learningmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.learningmanagementsystem.course.dto.CreateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.UpdateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.settings.CreateCourseSettingsRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void createCourse_givenCreateCourseRequestDto_shouldCreateCourseAndReturn201() throws Exception {
        var settings = new CreateCourseSettingsRequestDto(LocalDateTime.now(), LocalDateTime.now().plusDays(1), true);
        var request = new CreateCourseRequestDto("title", "description",
                new BigDecimal(250), settings);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.price").value(new BigDecimal(250)));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getCourse_givenId_shouldReturnCourseAndReturn200() throws Exception {
        mockMvc.perform(get("/courses/{id}", "b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(new BigDecimal(50)))
                .andExpect(jsonPath("$.title").value("Java Course"))
                .andExpect(jsonPath("$.description").value("The mose useful description"));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllCourses_shouldReturnAllCoursesAndReturn200() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateCourse_givenUpdateCourseRequestDto_shouldUpdateCourseAndReturn200() throws Exception {
        var request = new UpdateCourseRequestDto("New Title", "New Description", BigDecimal.valueOf(20));

        mockMvc.perform(put("/courses/{id}", "b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.description").value("New Description"))
                .andExpect(jsonPath("$.price").value(new BigDecimal(20)));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteCourse_givenId_shouldDeleteCourseAndReturn204AndReturn404() throws Exception {
        mockMvc.perform(delete("/courses/{id}", "b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d"))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/courses/{id}", "b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d"))
                .andExpect(status().isNotFound());
    }

}
