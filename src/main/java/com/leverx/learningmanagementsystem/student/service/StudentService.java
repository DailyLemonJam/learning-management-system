package com.leverx.learningmanagementsystem.student.service;

import com.leverx.learningmanagementsystem.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StudentService {

    Student create(Student student);

    Student get(UUID id);

    Page<Student> getAll(Pageable pageable);

    Student update(UUID id, Student student);

    void delete(UUID id);
}
