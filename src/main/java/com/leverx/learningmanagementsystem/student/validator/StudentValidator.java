package com.leverx.learningmanagementsystem.student.validator;

import com.leverx.learningmanagementsystem.course.exception.EntityValidationException;
import com.leverx.learningmanagementsystem.student.model.Student;
import com.leverx.learningmanagementsystem.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    public void onUpdate(UUID studentId, Student student) {
        var foundStudent = studentRepository.findByEmail(student.getEmail());
        if (foundStudent.isEmpty() || studentId.equals(foundStudent.get().getId())) {
            return;
        }
        throw new EntityValidationException("New Email is already in use");
    }

}
