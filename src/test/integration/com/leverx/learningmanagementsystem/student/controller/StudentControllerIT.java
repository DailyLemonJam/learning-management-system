package com.leverx.learningmanagementsystem.student.controller;

import com.leverx.learningmanagementsystem.AbstractConfigurationIT;
import com.leverx.learningmanagementsystem.util.StudentUtil;
import com.leverx.learningmanagementsystem.student.dto.CreateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.dto.UpdateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.repository.StudentRepository;
import com.leverx.learningmanagementsystem.core.security.role.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerIT extends AbstractConfigurationIT {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        studentRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    public void createStudent_givenCreateStudentRequestDto_shouldReturnStudentAndReturn201() throws Exception {
        // given
        var requestDto = new CreateStudentRequestDto("John", "Johnson",
                "validemail@email.com", LocalDate.now().minusDays(365 * 25),
                Locale.ENGLISH);

        // when
        var result = mockMvc.perform(post("/students")
                        .with(csrf())
                        .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)));

        var getResult = mockMvc.perform(get("/students")
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.firstName").value("John"));
        result.andExpect(jsonPath("$.lastName").value("Johnson"));
        result.andExpect(jsonPath("$.coins").value(new BigDecimal(0)));

        getResult.andExpect(status().isOk());
        getResult.andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    public void getStudent_givenId_shouldReturnStudentAndReturn200() throws Exception {
        // given
        var student = StudentUtil.createStudent(new ArrayList<>());
        var savedStudent = studentRepository.save(student);

        // when
        var result = mockMvc.perform(get("/students/{id}", savedStudent.getId())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName").value("John"));
        result.andExpect(jsonPath("$.lastName").value("Doe"));
        result.andExpect(jsonPath("$.dateOfBirth").value(LocalDate.of(2000, 5, 8).toString()));
        result.andExpect(jsonPath("$.coins").value(BigDecimal.valueOf(50)));
    }

    @Test
    public void getAllStudents_shouldReturnAllStudentsAndReturn200() throws Exception {// given
        var student = StudentUtil.createStudent(new ArrayList<>());
        studentRepository.save(student);

        // when
        var result = mockMvc.perform(get("/students")
                .param("sort", "lastName,asc")
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    public void updateStudent_givenUpdateStudentRequestDto_shouldUpdateStudentAndReturn200() throws Exception {
        // given
        var student = StudentUtil.createStudent(new ArrayList<>());
        var savedStudent = studentRepository.save(student);

        var localDate = LocalDate.now().minusDays(365 * 24);
        var request = new UpdateStudentRequestDto("New not last name", "New not first name",
                "email@email.com", localDate, Locale.ENGLISH);

        // when
        var result = mockMvc.perform(put("/students/{id}", savedStudent.getId())
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
    public void deleteStudent_givenId_shouldDeleteStudentAndReturn204AndReturn404() throws Exception {
        // given
        var student = StudentUtil.createStudent(new ArrayList<>());
        var savedStudent = studentRepository.save(student);

        // when
        var result = mockMvc.perform(delete("/students/{id}", savedStudent.getId())
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));
        var deleteResult = mockMvc.perform(delete("/students/{id}", savedStudent.getId())
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isNoContent());
        deleteResult.andExpect(status().isNotFound());
    }

}
