package com.leverx.learningmanagementsystem.service.payments;

import com.leverx.learningmanagementsystem.dto.payment.PurchaseCourseRequest;
import com.leverx.learningmanagementsystem.exception.EntityNotFoundException;
import com.leverx.learningmanagementsystem.exception.EntityValidationException;
import com.leverx.learningmanagementsystem.exception.PurchaseDeniedException;
import com.leverx.learningmanagementsystem.repository.CourseRepository;
import com.leverx.learningmanagementsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl implements PaymentsService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public void purchaseCourse(UUID courseId, PurchaseCourseRequest request) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        var student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        if (student.getCourses().contains(course)) {
            throw new EntityValidationException("Student is already in this course");
        }
        var studentCoins = student.getCoins();
        var coursePrice = course.getPrice();
        int comparison = studentCoins.compareTo(coursePrice);
        if (comparison < 0) {
            throw new PurchaseDeniedException("Not enough coins");
        }
        student.setCoins(studentCoins.subtract(coursePrice));
        student.getCourses().add(course);
        studentRepository.save(student);
    }
}
