package com.leverx.learningmanagementsystem.course.mapper;

import com.leverx.learningmanagementsystem.course.dto.CourseResponseDto;
import com.leverx.learningmanagementsystem.course.dto.CreateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.UpdateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    private final CourseSettingsMapper courseSettingsMapper;

    public CourseResponseDto toDto(Course course) {
        return new CourseResponseDto(
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

    public List<CourseResponseDto> toDtos(List<Course> courses) {
        var dtos = new ArrayList<CourseResponseDto>();
        courses.forEach(course -> dtos.add(toDto(course)));
        return dtos;
    }

    public Course toModel(CreateCourseRequestDto request) {
        var settings = CourseSettings.builder()
                .startDate(request.createCourseSettingsRequestDto().startDate())
                .endDate(request.createCourseSettingsRequestDto().endDate())
                .isPublic(request.createCourseSettingsRequestDto().isPublic())
                .build();
        return Course.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .coinsPaid(new BigDecimal(0))
                .lessons(new ArrayList<>())
                .students(new ArrayList<>())
                .courseSettings(settings)
                .build();
    }

    public Course toModel(UpdateCourseRequestDto request) {
        return Course.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .build();
    }

}
