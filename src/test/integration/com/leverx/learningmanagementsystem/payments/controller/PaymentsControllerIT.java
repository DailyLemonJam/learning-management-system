package com.leverx.learningmanagementsystem.payments.controller;

import com.leverx.learningmanagementsystem.common.AbstractConfigurationIT;
import com.leverx.learningmanagementsystem.common.UtilsIT;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.payments.dto.PurchaseCourseRequestDto;
import com.leverx.learningmanagementsystem.student.repository.StudentRepository;
import com.leverx.learningmanagementsystem.core.security.role.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentsControllerIT extends AbstractConfigurationIT {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    protected void setUp() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @AfterEach
    protected void tearDown() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    public void purchaseCourse_givenCourseIdAndPurchaseCourseRequestDto_shouldReturnCourseNotFound() throws Exception {
        // given
        var courseId = UUID.randomUUID();

        var student = UtilsIT.createStudent(new ArrayList<>());
        var savedStudent = studentRepository.save(student);
        var studentId = savedStudent.getId();

        var purchaseCourseRequest = new PurchaseCourseRequestDto(studentId);

        // when
        var result = mockMvc.perform(post("/payments/purchase-course/{courseId}", courseId)
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseCourseRequest)));

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    public void purchaseCourse_givenCourseIdAndPurchaseCourseRequestDto_shouldReturnStudentNotFound() throws Exception {
        // given
        var course = UtilsIT.createCourse();
        var savedCourse = courseRepository.save(course);
        var courseId = savedCourse.getId();

        var studentId = UUID.randomUUID();

        var purchaseCourseRequest = new PurchaseCourseRequestDto(studentId);

        // when
        var result = mockMvc.perform(post("/payments/purchase-course/{courseId}", courseId)
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseCourseRequest)));

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    public void purchaseCourse_givenCourseIdAndPurchaseCourseRequestDto_shouldReturnNotEnoughCoins() throws Exception {
        // given
        var course = UtilsIT.createCourse();
        var savedCourse = courseRepository.save(course);
        var courseId = savedCourse.getId();

        var student = UtilsIT.createStudent(new ArrayList<>());
        student.setCoins(BigDecimal.ZERO);
        var savedStudent = studentRepository.save(student);
        var studentId = savedStudent.getId();

        var purchaseCourseRequest = new PurchaseCourseRequestDto(studentId);

        // when
        var result = mockMvc.perform(post("/payments/purchase-course/{courseId}", courseId)
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseCourseRequest)));

        // then
        result.andExpect(status().isPaymentRequired());
    }

    @Test
    public void purchaseCourse_givenCourseIdAndPurchaseCourseRequestDto_shouldReturnStudentAlreadyEnrolled() throws Exception {
        // given
        var course = UtilsIT.createCourse();
        var savedCourse = courseRepository.save(course);
        var courseId = savedCourse.getId();

        var student = UtilsIT.createStudent(List.of(course));
        var savedStudent = studentRepository.save(student);
        var studentId = savedStudent.getId();

        var purchaseCourseRequest = new PurchaseCourseRequestDto(studentId);

        // when
        var result = mockMvc.perform(post("/payments/purchase-course/{courseId}", courseId)
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseCourseRequest)));

        // then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void purchaseCourse_givenCourseIdAndPurchaseCourseRequestDto_shouldEnrollStudentAndTransferCoinsAndReturn200() throws Exception {
        // given
        var coursePrice = BigDecimal.valueOf(200);
        var course = UtilsIT.createCourse();
        course.setCoinsPaid(BigDecimal.ZERO);
        course.setPrice(coursePrice);
        var savedCourse = courseRepository.save(course);
        var courseId = savedCourse.getId();

        var studentStartCoins = BigDecimal.valueOf(Long.MAX_VALUE);
        var student = UtilsIT.createStudent(new ArrayList<>());
        student.setCoins(studentStartCoins);
        var savedStudent = studentRepository.save(student);
        var studentId = savedStudent.getId();

        var purchaseCourseRequest = new PurchaseCourseRequestDto(studentId);

        // when
        var result = mockMvc.perform(post("/payments/purchase-course/{courseId}", courseId)
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseCourseRequest)));

        var studentResult = studentRepository.findById(studentId).orElseThrow();

        var courseResult = courseRepository.findById(courseId).orElseThrow();

        // then
        result.andExpect(status().isOk());

        assertEquals(studentStartCoins.subtract(coursePrice), studentResult.getCoins());
        assertEquals(courseId, studentResult.getCourses().getFirst().getId());

        assertEquals(coursePrice, courseResult.getCoinsPaid());
    }

}
