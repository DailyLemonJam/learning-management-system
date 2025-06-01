package com.leverx.learningmanagementsystem.student.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.learningmanagementsystem.web.security.role.Role;
import com.leverx.learningmanagementsystem.student.dto.CreateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.dto.UpdateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.model.Student;
import com.leverx.learningmanagementsystem.student.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("Integration")
class StudentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Value("${security.configuration.default-user.username}")
    private String defaultUserUsername;

    @Value("${security.configuration.default-user.password}")
    private String defaultUserPassword;

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
        var student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email(UUID.randomUUID() + "john.doe@example.com")
                .dateOfBirth(LocalDate.of(2000, 5, 8))
                .coins(BigDecimal.valueOf(50))
                .courses(new ArrayList<>())
                .build();
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
        var student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email(UUID.randomUUID() + "john.doe@example.com")
                .dateOfBirth(LocalDate.of(2000, 5, 8))
                .coins(BigDecimal.valueOf(50))
                .courses(new ArrayList<>())
                .build();
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
        var student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email(UUID.randomUUID() + "john.doe@example.com")
                .dateOfBirth(LocalDate.of(2000, 5, 8))
                .coins(BigDecimal.valueOf(50))
                .courses(new ArrayList<>())
                .build();
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
        var student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email(UUID.randomUUID() + "john.doe@example.com")
                .dateOfBirth(LocalDate.of(2000, 5, 8))
                .coins(BigDecimal.valueOf(50))
                .courses(new ArrayList<>())
                .build();
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
