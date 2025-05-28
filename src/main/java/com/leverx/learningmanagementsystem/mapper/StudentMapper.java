package com.leverx.learningmanagementsystem.mapper;

import com.leverx.learningmanagementsystem.dto.student.CreateStudentRequestDto;
import com.leverx.learningmanagementsystem.dto.student.StudentResponseDto;
import com.leverx.learningmanagementsystem.dto.student.UpdateStudentRequestDto;
import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.model.Student;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public StudentResponseDto toDto(Student student) {
        return new StudentResponseDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getDateOfBirth(),
                student.getCoins(),
                student.getCourses().stream()
                        .map(Course::getId)
                        .collect(Collectors.toList())
        );
    }

    public List<StudentResponseDto> toDtos(List<Student> students) {
        var dtos = new ArrayList<StudentResponseDto>();
        students.forEach(student -> dtos.add(toDto(student)));
        return dtos;
    }

    public Student toModel(CreateStudentRequestDto request) {
        return Student.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .courses(new ArrayList<>())
                .coins(new BigDecimal(0))
                .build();
    }

    public Student toModel(UpdateStudentRequestDto request) {
        return Student.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .build();
    }

}
