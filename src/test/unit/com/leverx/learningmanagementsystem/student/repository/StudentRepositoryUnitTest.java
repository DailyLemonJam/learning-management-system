package com.leverx.learningmanagementsystem.student.repository;

import com.leverx.learningmanagementsystem.student.model.Student;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Tag("Unit")
public class StudentRepositoryUnitTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .locale(Locale.ENGLISH)
                .email("email@gmail.com")
                .coins(BigDecimal.valueOf(150))
                .dateOfBirth(LocalDate.EPOCH)
                .courses(new ArrayList<>())
                .build();
        studentRepository.save(student);
    }

    @AfterEach
    public void tearDown() {
        studentRepository.delete(student);
    }

    @Test
    public void createStudent_givenStudent_ShouldCreateAndReturnStudent() {
        // given
        var newStudent = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .locale(Locale.ENGLISH)
                .email("email@gmail.com")
                .coins(BigDecimal.valueOf(50))
                .dateOfBirth(LocalDate.EPOCH)
                .courses(new ArrayList<>())
                .build();

        // when
        var savedStudent = studentRepository.save(newStudent);

        // then
        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getId());
        assertEquals(BigDecimal.valueOf(50), savedStudent.getCoins());
        assertEquals("email@gmail.com", savedStudent.getEmail());
        assertEquals("Doe", savedStudent.getLastName());
    }

    @Test
    public void findById_givenStudentId_ShouldFindAndReturnStudent() {
        // given
        var id = student.getId();

        // when
        var foundStudent = studentRepository.findById(id).orElse(null);

        // then
        assertNotNull(foundStudent);
        assertEquals(student.getId(), foundStudent.getId());
        assertEquals(BigDecimal.valueOf(150), foundStudent.getCoins());
        assertEquals("John", foundStudent.getFirstName());
    }

    @Test
    public void updateStudent_givenStudent_ShouldUpdateAndReturnStudent() {
        // given
        var newStudentData = Student.builder()
                .firstName("Bob")
                .lastName("Smith")
                .locale(Locale.FRENCH)
                .email("newemail@gmail.com")
                .coins(BigDecimal.valueOf(250))
                .dateOfBirth(LocalDate.now().minusDays(250))
                .courses(new ArrayList<>())
                .build();

        // when
        var studentToUpdate = studentRepository.findById(student.getId())
                .orElseThrow(EntityNotFoundException::new);
        studentToUpdate.setLocale(newStudentData.getLocale());
        studentToUpdate.setFirstName(newStudentData.getFirstName());
        studentToUpdate.setLastName(newStudentData.getLastName());
        studentToUpdate.setEmail(newStudentData.getEmail());
        studentToUpdate.setCoins(newStudentData.getCoins());
        studentToUpdate.setDateOfBirth(newStudentData.getDateOfBirth());
        studentRepository.save(studentToUpdate);

        // then
        assertEquals(student.getId(), studentToUpdate.getId());
        assertEquals("Bob", student.getFirstName());
        assertEquals("Smith", student.getLastName());
        assertEquals("newemail@gmail.com", student.getEmail());
        assertEquals(BigDecimal.valueOf(250), student.getCoins());
    }

    @Test
    public void deleteStudent_givenStudentId_ShouldDeleteAndReturnNothing() {
        // given
        var id = student.getId();

        // when
        var studentToDelete = studentRepository.findById(id)
                        .orElseThrow(EntityNotFoundException::new);
        studentRepository.delete(studentToDelete);

        var thisStudentShouldBeNull = studentRepository.findById(id)
                .orElse(null);

        // then
        assertNull(thisStudentShouldBeNull);
    }

    @Test
    public void findByEmail_givenStudentEmail_ShouldReturnStudent() {
        // given
        var email = student.getEmail();

        // when
        var foundStudent = studentRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);

        // then
        assertEquals(student.getEmail(), foundStudent.getEmail());
    }

}
