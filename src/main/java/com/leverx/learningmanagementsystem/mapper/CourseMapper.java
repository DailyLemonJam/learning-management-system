package com.leverx.learningmanagementsystem.mapper;

import com.leverx.learningmanagementsystem.dto.course.CourseDto;
import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.model.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseMapper implements DtoMapper<Course, CourseDto> {
    private final CourseSettingsMapper courseSettingsMapper;

    @Override
    public CourseDto toDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                courseSettingsMapper.toDto(course.getCourseSettings()),
                course.getLessons().stream()
                        .map(Lesson::getId)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<CourseDto> toDto(List<Course> courses) {
        var dtos = new ArrayList<CourseDto>();
        for (var course : courses) {
            dtos.add(toDto(course));
        }
        return dtos;
    }

}
