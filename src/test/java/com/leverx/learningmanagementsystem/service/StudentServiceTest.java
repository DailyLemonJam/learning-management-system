package com.leverx.learningmanagementsystem.service;

import com.leverx.learningmanagementsystem.model.Student;
import com.leverx.learningmanagementsystem.service.student.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.now().minusDays(365 * 25))
                .coins(BigDecimal.valueOf(100)) // should set because of NULL constraints (get rid of constraints in Liquibase?)
                .email("john.doe@email.com")
                .build();
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void createStudent_givenStudent_shouldReturnStudent() {
        var createdStudent = studentService.create(student);

        assertNotNull(createdStudent);
        assertEquals(createdStudent.getFirstName(), student.getFirstName());
        assertEquals(createdStudent.getLastName(), student.getLastName());
        assertEquals(createdStudent.getEmail(), student.getEmail());
        assertEquals(createdStudent.getDateOfBirth(), student.getDateOfBirth());
        assertEquals(createdStudent.getCoins(), student.getCoins());
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getStudent_givenId_shouldReturnStudent() {
        var foundStudent = studentService.get(UUID.fromString("7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59"));
        assertNotNull(foundStudent);
        assertEquals("7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59", foundStudent.getId().toString());
        assertEquals("John", foundStudent.getFirstName());
        assertEquals("Doe", foundStudent.getLastName());
        assertEquals("john.doe@example.com", foundStudent.getEmail());
        assertEquals("2000-05-06", foundStudent.getDateOfBirth().toString());
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllStudents_shouldReturnAllStudent() {
        var students = studentService.get();

        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals("7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59", students.getFirst().getId().toString());
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateStudent_shouldUpdateStudentAndReturnStudent() {
        var updatedStudent = studentService.update(UUID.fromString("7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59"), student);
        assertNotNull(updatedStudent);
        assertEquals(updatedStudent.getFirstName(), student.getFirstName());
        assertEquals(updatedStudent.getLastName(), student.getLastName());
        assertEquals(updatedStudent.getEmail(), student.getEmail());
        assertEquals(updatedStudent.getDateOfBirth(), student.getDateOfBirth());
    }

    @Test
    @Sql(scripts = {"/data/clear-db.sql", "/data/init-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteStudent_shouldDeleteStudent() {
        studentService.delete(UUID.fromString("7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59"));

        var students = studentService.get();
        assertEquals(0, students.size());
    }

}
