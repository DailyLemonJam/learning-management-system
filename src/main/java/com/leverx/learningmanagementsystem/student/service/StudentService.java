package com.leverx.learningmanagementsystem.student.service;

import com.leverx.learningmanagementsystem.student.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    Student create(Student student);

    Student get(UUID id);

    List<Student> get();

    Student update(UUID id, Student student);

    void delete(UUID id);

}
