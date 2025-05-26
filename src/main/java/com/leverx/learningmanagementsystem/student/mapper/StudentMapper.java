package com.leverx.learningmanagementsystem.student.mapper;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.student.dto.CreateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.dto.StudentResponseDto;
import com.leverx.learningmanagementsystem.student.dto.UpdateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
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
                student.getLanguage(),
                student.getCourses().stream()
                        .map(Course::getId)
                        .collect(Collectors.toList())
        );
    }

    public Page<StudentResponseDto> toDtos(Page<Student> students) {
        return students.map(this::toDto);
    }

    public Student toModel(CreateStudentRequestDto request) {
        return Student.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .language(request.language())
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
                .language(request.language())
                .build();
    }

}
