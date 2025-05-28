package com.leverx.learningmanagementsystem.unit.service;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.course.service.CourseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplUnitTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        courseId = UUID.randomUUID();
        course = new Course();
        course.setId(courseId);
        course.setCourseSettings(new CourseSettings());
        course.setTitle("Test Course");
    }

    @Test
    void createCourse_shouldSaveCourse() {
        // when
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        var savedCourse = courseService.create(course);

        // then
        assertNotNull(savedCourse);
        assertEquals(course.getTitle(), savedCourse.getTitle());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void getCourse_shouldReturnCourse() {
        // when
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        var foundCourse = courseService.get(courseId);

        // then
        assertNotNull(foundCourse);
        assertEquals(course.getTitle(), foundCourse.getTitle());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void getCourse_shouldThrowException() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.get(courseId));
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void getAllCourses_shouldReturnCoursesPage() {
        // given
        var pageable = PageRequest.of(0, 5);
        var courses = List.of(
                Course.builder()
                        .courseSettings(CourseSettings.builder().build())
                        .description("Course")
                        .price(BigDecimal.valueOf(60))
                        .lessons(new ArrayList<>())
                        .build(),
                Course.builder()
                        .courseSettings(CourseSettings.builder().build())
                        .description("Course 2")
                        .price(BigDecimal.valueOf(80))
                        .lessons(new ArrayList<>())
                        .build()
        );
        var coursePage = new PageImpl<>(courses, pageable, courses.size());

        // when
        when(courseRepository.findAll(any(Pageable.class))).thenReturn(coursePage);
        var pageResult = courseService.getAll(Pageable.unpaged());

        // then
        assertNotNull(pageResult);
        verify(courseRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void updateCourse_shouldUpdateCourse() {
        // when
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        var updatedCourse = courseService.update(courseId, course);

        // then
        assertNotNull(updatedCourse);
        assertEquals(course.getTitle(), updatedCourse.getTitle());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void deleteCourse_shouldRemoveCourse() {
        // when
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        courseService.delete(courseId);

        // then
        verify(courseRepository, times(1)).delete(course);
    }

}
