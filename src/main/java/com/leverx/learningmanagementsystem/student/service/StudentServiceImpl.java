package com.leverx.learningmanagementsystem.student.service;

import com.leverx.learningmanagementsystem.student.model.Student;
import com.leverx.learningmanagementsystem.student.repository.StudentRepository;
import com.leverx.learningmanagementsystem.student.validator.StudentValidator;
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
    private final StudentValidator studentValidator;

    @Transactional
    @Override
    public Student create(Student student) {
        studentValidator.onCreate(student);

        return studentRepository.save(student);
    }

    @Override
    public Student get(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find student with id: " + id));
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Transactional
    @Override
    public Student update(UUID id, Student student) {
        studentValidator.onUpdate(student);

        var existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find student with id: " + id));
        return updateStudent(existingStudent, student);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find student with id: " + id));
        studentRepository.delete(student);
    }

    private Student updateStudent(Student existingStudent, Student student) {
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        existingStudent.setLanguage(student.getLanguage());
        return studentRepository.save(existingStudent);
    }

}
