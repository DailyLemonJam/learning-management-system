package com.leverx.learningmanagementsystem.course.service;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.course.common.builder.CourseBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void createCourse_shouldSaveCourse() {
        // given
        var course = CourseBuilder.createCourse();
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
        var course = CourseBuilder.createCourse();
        var courseId = UUID.randomUUID();
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
        var courseId = UUID.randomUUID();
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
                CourseBuilder.createCourse(),
                CourseBuilder.createCourse()
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
        var course = CourseBuilder.createCourse();
        var courseId = UUID.randomUUID();
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
        var course = CourseBuilder.createCourse();
        var courseId = UUID.randomUUID();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // when
        courseService.delete(courseId);

        // then
        verify(courseRepository, times(1)).delete(course);
    }
}
