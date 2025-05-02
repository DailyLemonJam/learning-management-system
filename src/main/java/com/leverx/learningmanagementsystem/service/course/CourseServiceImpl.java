package com.leverx.learningmanagementsystem.service.course;

import com.leverx.learningmanagementsystem.dto.course.CourseDto;
import com.leverx.learningmanagementsystem.dto.course.CreateCourseRequest;
import com.leverx.learningmanagementsystem.dto.course.UpdateCourseRequest;
import com.leverx.learningmanagementsystem.exception.EntityNotFoundException;
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
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseSettingsRepository courseSettingsRepository;
    private final CourseMapper courseMapper;

    @Transactional
    @Override
    public CourseDto create(CreateCourseRequest request) {
        var settings = saveCourseSettings(request);
        var course = saveCourse(request, settings);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDto get(UUID id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course with id: " + id));
        return courseMapper.toDto(course);
    }

    @Override
    public List<CourseDto> get() {
        var courses = courseRepository.findAll();
        return courseMapper.toDto(courses);
    }

    @Transactional
    @Override
    public CourseDto update(UUID id, UpdateCourseRequest request) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course with id: " + id));
        var updatedCourse = updateCourse(course, request);
        return courseMapper.toDto(updatedCourse);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course with id: " + id));
        courseRepository.delete(course);
    }

    private CourseSettings saveCourseSettings(CreateCourseRequest request) {
        var settings = CourseSettings.builder()
                .startDate(request.createCourseSettingsRequest().startDate())
                .endDate(request.createCourseSettingsRequest().endDate())
                .isPublic(request.createCourseSettingsRequest().isPublic())
                .build();
        return courseSettingsRepository.save(settings);
    }

    private Course saveCourse(CreateCourseRequest request, CourseSettings settings) {
        var course = Course.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .coinsPaid(new BigDecimal(0))
                .courseSettings(settings)
                .lessons(new ArrayList<>())
                .build();
        return courseRepository.save(course);
    }

    private Course updateCourse(Course course, UpdateCourseRequest request) {
        course.setTitle(request.newTitle());
        course.setDescription(request.newDescription());
        course.setPrice(request.newPrice());
        return courseRepository.save(course);
    }
}
