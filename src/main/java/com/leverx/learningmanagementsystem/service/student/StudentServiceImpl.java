package com.leverx.learningmanagementsystem.service.student;

import com.leverx.learningmanagementsystem.dto.student.CreateStudentRequest;
import com.leverx.learningmanagementsystem.dto.student.StudentDto;
import com.leverx.learningmanagementsystem.dto.student.UpdateStudentRequest;
import com.leverx.learningmanagementsystem.exception.EntityNotFoundException;
import com.leverx.learningmanagementsystem.exception.EntityValidationException;
import com.leverx.learningmanagementsystem.mapper.StudentMapper;
import com.leverx.learningmanagementsystem.model.Student;
import com.leverx.learningmanagementsystem.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Transactional
    @Override
    public StudentDto create(CreateStudentRequest request) {
        if (studentRepository.existsByEmail(request.email())) {
            throw new EntityValidationException("Email already exists");
        }
        var createdStudent = saveStudent(request);
        return studentMapper.toDto(createdStudent);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentDto get(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find student with id: " + id));
        return studentMapper.toDto(student);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentDto> get() {
        var students = studentRepository.findAll();
        return studentMapper.toDto(students);
    }

    @Transactional
    @Override
    public StudentDto update(UUID id, UpdateStudentRequest request) {
        if (studentRepository.existsByEmail(request.newEmail())) {
            throw new EntityValidationException("New email already exists");
        }
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find student with id: " + id));
        var updatedStudent = updateStudent(student, request);
        return studentMapper.toDto(updatedStudent);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find student with id: " + id));
        studentRepository.delete(student);
    }

    private Student saveStudent(CreateStudentRequest request) {
        var student = Student.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .courses(new ArrayList<>())
                .coins(new BigDecimal(0))
                .build();
        return studentRepository.save(student);
    }

    private Student updateStudent(Student student, UpdateStudentRequest request) {
        student.setFirstName(request.newFirstName());
        student.setLastName(request.newLastName());
        student.setEmail(request.newEmail());
        student.setDateOfBirth(request.newDateOfBirth());
        return studentRepository.save(student);
    }

}
