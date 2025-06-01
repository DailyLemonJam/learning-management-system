package com.leverx.learningmanagementsystem.course.service;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    @Override
    public Course create(Course course) {
        course.getCourseSettings().setCourse(course);
        return courseRepository.save(course);
    }

    @Override
    public Course get(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course with id: " + id));
    }

    @Override
    public Page<Course> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
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
