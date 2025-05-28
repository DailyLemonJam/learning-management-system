package com.leverx.learningmanagementsystem.service.student;

import com.leverx.learningmanagementsystem.exception.EntityValidationException;
import com.leverx.learningmanagementsystem.model.Student;
import com.leverx.learningmanagementsystem.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Transactional
    @Override
    public Student create(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new EntityValidationException("Email already exists");
        }
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    @Override
    public Student get(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find student with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Student> get() {
        return studentRepository.findAll();
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find student with id: " + id));
        studentRepository.delete(student);
    }

    @Transactional
    @Override
    public Student update(UUID id, Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new EntityValidationException("New email already exists");
        }
        var existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find student with id: " + id));
        return updateStudent(existingStudent, student);
    }

    private Student updateStudent(Student existingStudent, Student student) {
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        return studentRepository.save(existingStudent);
    }

}
