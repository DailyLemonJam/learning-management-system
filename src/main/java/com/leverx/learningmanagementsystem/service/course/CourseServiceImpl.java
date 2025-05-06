package com.leverx.learningmanagementsystem.service.course;

import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.repository.CourseRepository;
import com.leverx.learningmanagementsystem.repository.CourseSettingsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseSettingsRepository courseSettingsRepository;

    @Transactional
    @Override
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    @Override
    public Course get(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> get() {
        return courseRepository.findAll();
    }

    @Transactional
    @Override
    public Course update(UUID id, Course course) {
        var existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course with id: " + id));
        return updateCourse(existingCourse, course);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course with id: " + id));
        courseRepository.delete(course);
    }

    private Course updateCourse(Course existingCourse, Course course) {
        existingCourse.setTitle(course.getTitle());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setPrice(course.getPrice());
        return courseRepository.save(existingCourse);
    }
}
