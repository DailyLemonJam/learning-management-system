package com.leverx.learningmanagementsystem.service.payments;

import com.leverx.learningmanagementsystem.exception.*;
import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.model.Student;
import com.leverx.learningmanagementsystem.repository.CourseRepository;
import com.leverx.learningmanagementsystem.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl implements PaymentsService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public void purchaseCourse(UUID courseId, UUID studentId) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        var coursePrice = course.getPrice();
        var studentCoins = student.getCoins();

        validateCourseEnrollment(course, studentId);
        validateStudentBalance(coursePrice, studentCoins);

        transferCoins(course, student);
        completePurchase(course, student);
    }

    private void validateCourseEnrollment(Course course, UUID studentId) {
        if (isStudentEnrolledInCourse(course, studentId)) {
            throw new StudentAlreadyEnrolledException("Student is already enrolled in the course.");
        }
    }

    private boolean isStudentEnrolledInCourse(Course course, UUID studentId) {
        var students = new HashSet<>(course.getStudents());
        return students.stream().anyMatch(it -> it.getId().equals(studentId));
    }

    private void validateStudentBalance(BigDecimal coursePrice, BigDecimal studentCoins) {
        if (studentCoins.compareTo(coursePrice) < 0) {
            throw new NotEnoughCoinsException(coursePrice.toString());
        }
    }

    private void transferCoins(Course course, Student student) {
        var coursePrice = course.getPrice();

        student.setCoins(student.getCoins().subtract(coursePrice));
        course.setCoinsPaid(course.getCoinsPaid().add(coursePrice));
    }

    private void completePurchase(Course course, Student student) {
        student.getCourses().add(course);
        studentRepository.save(student);
    }
}
