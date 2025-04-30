package com.leverx.learningmanagementsystem.service;

import com.leverx.learningmanagementsystem.dto.course.CourseDto;
import com.leverx.learningmanagementsystem.dto.course.CreateCourseRequest;
import com.leverx.learningmanagementsystem.dto.course.UpdateCourseRequest;
import com.leverx.learningmanagementsystem.exception.CourseNotFoundException;
import com.leverx.learningmanagementsystem.mapper.CourseMapper;
import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.model.CourseSettings;
import com.leverx.learningmanagementsystem.repository.CourseRepository;
import com.leverx.learningmanagementsystem.repository.CourseSettingsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseSettingsRepository courseSettingsRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseDto createCourse(CreateCourseRequest request) {
        var courseSettings = CourseSettings.builder()
                .startDate(request.createCourseSettingsRequest().startDate())
                .endDate(request.createCourseSettingsRequest().endDate())
                .isPublic(request.createCourseSettingsRequest().isPublic())
                .build();
        courseSettingsRepository.save(courseSettings);
        var course = Course.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .coinsPaid(new BigDecimal(0))
                .courseSettings(courseSettings)
                .lessons(new ArrayList<>())
                .build();
        var createdCourse = courseRepository.save(course);
        return courseMapper.toDto(createdCourse);
    }

    public CourseDto getCourse(UUID id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Can't find course with id: " + id));
        return courseMapper.toDto(course);
    }

    public List<CourseDto> getCourses() {
        var courses = courseRepository.findAll();
        return courseMapper.toDto(courses);
    }

    @Transactional
    public CourseDto updateCourse(UUID id, UpdateCourseRequest request) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Can't find course with id: " + id));
        var newCourseSettings = CourseSettings.builder()
                .startDate(request.newCourseSettings().newStartDate())
                .endDate(request.newCourseSettings().newEndDate())
                .isPublic(request.newCourseSettings().newIsPublic())
                .build();
        course.setTitle(request.newTitle());
        course.setDescription(request.newDescription());
        course.setPrice(request.newPrice());
        course.setCourseSettings(newCourseSettings);
        var updatedCourse = courseRepository.save(course);
        return courseMapper.toDto(updatedCourse);
    }

    @Transactional
    public void deleteCourse(UUID id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Can't find course with id: " + id));
        courseRepository.delete(course);
    }

}
