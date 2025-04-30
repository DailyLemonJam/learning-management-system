package com.leverx.learningmanagementsystem.mapper;

import com.leverx.learningmanagementsystem.dto.student.StudentDto;
import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper implements DtoMapper<Student, StudentDto> {

    @Override
    public StudentDto toDto(Student student) {
        return new StudentDto(
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

    @Override
    public List<StudentDto> toDto(List<Student> students) {
        var dtos = new ArrayList<StudentDto>();
        for (var student : students) {
            dtos.add(toDto(student));
        }
        return dtos;
    }

}
