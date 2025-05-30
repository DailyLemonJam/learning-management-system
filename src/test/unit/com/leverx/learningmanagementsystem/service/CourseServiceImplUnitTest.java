package com.leverx.learningmanagementsystem.service;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.course.service.CourseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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
@Tag("Unit")
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
        // given
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // when
        var savedCourse = courseService.create(course);

        // then
        assertNotNull(savedCourse);
        assertEquals(course.getTitle(), savedCourse.getTitle());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void getCourse_shouldReturnCourse() {
        // given
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // when
        var foundCourse = courseService.get(courseId);

        // then
        assertNotNull(foundCourse);
        assertEquals(course.getTitle(), foundCourse.getTitle());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void getCourse_shouldThrowException() {
        // given
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // when
        assertThrows(EntityNotFoundException.class, () -> courseService.get(courseId));

        // then
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
        when(courseRepository.findAll(any(Pageable.class))).thenReturn(coursePage);

        // when
        var pageResult = courseService.getAll(Pageable.unpaged());

        // then
        assertNotNull(pageResult);
        verify(courseRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void updateCourse_shouldUpdateCourse() {
        // given
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // when
        var updatedCourse = courseService.update(courseId, course);

        // then
        assertNotNull(updatedCourse);
        assertEquals(course.getTitle(), updatedCourse.getTitle());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void deleteCourse_shouldRemoveCourse() {
        // given
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // when
        courseService.delete(courseId);

        // then
        verify(courseRepository, times(1)).delete(course);
    }

}
