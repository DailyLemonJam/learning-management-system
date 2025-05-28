package com.leverx.learningmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.learningmanagementsystem.security.role.Role;
import com.leverx.learningmanagementsystem.student.dto.CreateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.dto.UpdateStudentRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

    @Value("${security.configuration.default-user.username}")
    private String defaultUserUsername;

    @Value("${security.configuration.default-user.password}")
    private String defaultUserPassword;

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void createStudent_givenCreateStudentRequestDto_shouldReturnStudentAndReturn200() throws Exception {
        // given
        var requestDto = new CreateStudentRequestDto("John", "Johnson",
                "validemail@email.com", LocalDate.now().minusDays(365 * 25));

        // when
        var result = mockMvc.perform(post("/students")
                        .with(csrf())
                        .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.firstName").value("John"));
        result.andExpect(jsonPath("$.lastName").value("Johnson"));
        result.andExpect(jsonPath("$.coins").value(new BigDecimal(0)));
        mockMvc.perform(get("/students")
                        .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getStudent_givenId_shouldReturnStudentAndReturn200() throws Exception {
        // when
        var result = mockMvc.perform(get("/students/{id}", "7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59")
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName").value("John"));
        result.andExpect(jsonPath("$.lastName").value("Doe"));
        result.andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllStudents_shouldReturnAllStudentsAndReturn200() throws Exception {
        // when
        var result = mockMvc.perform(get("/students")
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateStudent_givenUpdateStudentRequestDto_shouldUpdateStudentAndReturn200() throws Exception {
        // given
        var localDate = LocalDate.now().minusDays(365 * 24);
        var request = new UpdateStudentRequestDto("New not last name", "New not first name",
                "email@email.com", localDate);

        // when
        var result = mockMvc.perform(put("/students/{id}", "7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59")
                        .with(csrf())
                        .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName").value("New not last name"));
        result.andExpect(jsonPath("$.lastName").value("New not first name"));
        result.andExpect(jsonPath("$.email").value("email@email.com"));
        result.andExpect(jsonPath("$.dateOfBirth").value(localDate.toString()));
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteStudent_givenId_shouldDeleteStudentAndReturn204AndReturn404() throws Exception {
        // when
        var result = mockMvc.perform(delete("/students/{id}", "7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59")
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isNoContent());

        // when
        result = mockMvc.perform(delete("/students/{id}", "7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59")
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isNotFound());
    }

}
