package com.leverx.learningmanagementsystem.student.controller;

import com.leverx.learningmanagementsystem.student.dto.CreateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.dto.StudentResponseDto;
import com.leverx.learningmanagementsystem.student.dto.UpdateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.mapper.StudentMapper;
import com.leverx.learningmanagementsystem.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "Student Controller", description = "Handles Student operations")
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create Student", description = "Creates new Student entity")
    public StudentResponseDto create(@Valid @RequestBody CreateStudentRequestDto request) {
        var student = studentMapper.toModel(request);
        var createdStudent = studentService.create(student);
        return studentMapper.toDto(createdStudent);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Get Student", description = "Returns Student information")
    public StudentResponseDto get(@PathVariable UUID id) {
        var student = studentService.get(id);
        return studentMapper.toDto(student);
    }

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Get Students", description = "Returns all Students")
    public Page<StudentResponseDto> getAll(Pageable pageable) {
        var students = studentService.getAll(pageable);
        return studentMapper.toDtos(students);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update Student", description = "Updates Student information")
    public StudentResponseDto update(@PathVariable UUID id,
                                     @Valid @RequestBody UpdateStudentRequestDto request) {
        var student = studentMapper.toModel(request);
        var updatedStudent = studentService.update(id, student);
        return studentMapper.toDto(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Student", description = "Deletes Student entity from DB")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        studentService.delete(id);
    }

}
