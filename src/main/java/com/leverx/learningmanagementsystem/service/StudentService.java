package com.leverx.learningmanagementsystem.service;

import com.leverx.learningmanagementsystem.dto.student.CreateStudentRequest;
import com.leverx.learningmanagementsystem.dto.student.StudentDto;
import com.leverx.learningmanagementsystem.dto.student.UpdateStudentRequest;
import com.leverx.learningmanagementsystem.exception.EmailAlreadyExistsException;
import com.leverx.learningmanagementsystem.exception.StudentNotFoundException;
import com.leverx.learningmanagementsystem.mapper.StudentMapper;
import com.leverx.learningmanagementsystem.model.Student;
import com.leverx.learningmanagementsystem.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public StudentDto createStudent(CreateStudentRequest request) {
        if (studentRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        var student = Student.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .courses(new ArrayList<>())
                .coins(new BigDecimal(0))
                .build();
        var createdStudent = studentRepository.save(student);
        return studentMapper.toDto(createdStudent);
    }

    public StudentDto getStudent(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Can't find student with id: " + id));
        return studentMapper.toDto(student);
    }

    public List<StudentDto> getStudents() {
        var students = studentRepository.findAll();
        return studentMapper.toDto(students);
    }

    @Transactional
    public StudentDto updateStudent(UUID id, UpdateStudentRequest request) {
        if (studentRepository.existsByEmail(request.newEmail())) {
            throw new EmailAlreadyExistsException("New email already exists");
        }
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Can't find student with id: " + id));
        student.setFirstName(request.newFirstName());
        student.setLastName(request.newLastName());
        student.setEmail(request.newEmail());
        student.setDateOfBirth(request.newDateOfBirth());
        var updatedStudent = studentRepository.save(student);
        return studentMapper.toDto(updatedStudent);
    }

    @Transactional
    public void deleteStudent(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Can't find student with id: " + id));
        studentRepository.delete(student);
    }

}
