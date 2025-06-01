package com.leverx.learningmanagementsystem.student.validator;

import com.leverx.learningmanagementsystem.course.exception.EntityValidationException;
import com.leverx.learningmanagementsystem.student.model.Student;
import com.leverx.learningmanagementsystem.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentValidator {

    private final StudentRepository studentRepository;

    public void onCreate(Student student) {
        studentRepository.findByEmail(student.getEmail())
                .ifPresent(s -> {
                    throw new EntityValidationException("Email already exists");
                });
    }

    public void onUpdate(Student student) {
        studentRepository.findByEmail(student.getEmail())
                .ifPresent(s -> {
                    throw new EntityValidationException("New Email is already in use");
                });
    }

}
