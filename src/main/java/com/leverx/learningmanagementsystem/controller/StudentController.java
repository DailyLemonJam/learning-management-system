package com.leverx.learningmanagementsystem.controller;

import com.leverx.learningmanagementsystem.dto.student.CreateStudentRequestDto;
import com.leverx.learningmanagementsystem.dto.student.StudentResponseDto;
import com.leverx.learningmanagementsystem.dto.student.UpdateStudentRequestDto;
import com.leverx.learningmanagementsystem.mapper.StudentMapper;
import com.leverx.learningmanagementsystem.service.student.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
        var result = studentService.create(student);
        return studentMapper.toDto(result);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Get Student", description = "Returns Student information")
    public StudentResponseDto get(@PathVariable UUID id) {
        return studentMapper.toDto(studentService.get(id));
    }

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Get Students", description = "Returns all Students")
    public List<StudentResponseDto> get() {
        return studentMapper.toDtos(studentService.get());
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update Student", description = "Updates Student information")
    public StudentResponseDto update(@PathVariable UUID id,
                                     @Valid @RequestBody UpdateStudentRequestDto request) {
        var student = studentMapper.toModel(request);
        var result = studentService.update(id, student);
        return studentMapper.toDto(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Student", description = "Deletes Student entity from DB")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        studentService.delete(id);
    }

}
