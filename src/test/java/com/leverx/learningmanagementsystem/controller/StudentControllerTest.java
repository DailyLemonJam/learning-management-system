package com.leverx.learningmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.learningmanagementsystem.dto.course.UpdateCourseRequestDto;
import com.leverx.learningmanagementsystem.dto.student.CreateStudentRequestDto;
import com.leverx.learningmanagementsystem.dto.student.UpdateStudentRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void createStudent_givenCreateStudentRequestDto_shouldReturnStudentAndReturn200() throws Exception {
        var requestDto = new CreateStudentRequestDto("John", "Johnson",
                "validemail@email.com", LocalDate.now().minusDays(365 * 25));

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Johnson"))
                .andExpect(jsonPath("$.coins").value(new BigDecimal(0)));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getStudent_givenId_shouldReturnStudentAndReturn200() throws Exception {
        mockMvc.perform(get("/students/{id}", "7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllStudents_shouldReturnAllStudentsAndReturn200() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateStudent_givenUpdateStudentRequestDto_shouldUpdateStudentAndReturn200() throws Exception {
        var localDate = LocalDate.now().minusDays(365 * 24);
        var request = new UpdateStudentRequestDto("New not last name", "New not first name",
                "email@email.com", localDate);
        mockMvc.perform(put("/students/{id}", "7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("New not last name"))
                .andExpect(jsonPath("$.lastName").value("New not first name"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.dateOfBirth").value(localDate.toString()));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteStudent_givenId_shouldDeleteStudentAndReturn204AndReturn404() throws Exception {
        mockMvc.perform(delete("/students/{id}", "7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59"))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/students/{id}", "7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59"))
                .andExpect(status().isNotFound());
    }

}
