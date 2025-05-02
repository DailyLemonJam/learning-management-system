package com.leverx.learningmanagementsystem.service.student;

import com.leverx.learningmanagementsystem.dto.student.CreateStudentRequest;
import com.leverx.learningmanagementsystem.dto.student.StudentDto;
import com.leverx.learningmanagementsystem.dto.student.UpdateStudentRequest;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    StudentDto create(CreateStudentRequest request);

    StudentDto get(UUID id);

    List<StudentDto> get();

    StudentDto update(UUID id, UpdateStudentRequest request);

    void delete(UUID id);

}
