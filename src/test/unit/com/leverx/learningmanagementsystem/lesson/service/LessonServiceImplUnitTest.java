package com.leverx.learningmanagementsystem.lesson.service;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import com.leverx.learningmanagementsystem.lesson.model.VideoLesson;
import com.leverx.learningmanagementsystem.lesson.repository.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Unit")
public class LessonServiceImplUnitTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LessonServiceImpl lessonService;

    private Lesson videoLesson;
    private UUID lessonId;
    private UUID courseId;
    private Course course;

    @BeforeEach
    void setUp() {
        lessonId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        course = new Course();
        videoLesson = new VideoLesson();
        videoLesson.setId(lessonId);
    }

    @Test
    void createLesson_ShouldSaveLesson() {
        // when
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lessonRepository.save(videoLesson)).thenReturn(videoLesson);
        var createdLesson = lessonService.create(videoLesson, courseId);

        // then
        assertNotNull(createdLesson);
        assertEquals(course, createdLesson.getCourse());
        verify(lessonRepository).save(videoLesson);
    }

    @Test
    void getLesson_ShouldReturnLesson() {
        // when
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(videoLesson));
        var foundLesson = lessonService.get(lessonId);

        // then
        assertNotNull(foundLesson);
        assertEquals(lessonId, foundLesson.getId());
    }

    @Test
    void getLesson_ShouldThrowException() {
        // when
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> lessonService.get(lessonId));
    }

    @Test
    void getAllLessons_ShouldReturnLessonsPage() {
        // given
        var lessonPage = new PageImpl<>(Collections.singletonList(videoLesson));

        // when
        when(lessonRepository.findAll(any(Pageable.class))).thenReturn(lessonPage);
        var result = lessonService.getAll(Pageable.unpaged());

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void updateLesson_ShouldUpdateLesson() {
        // when
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(videoLesson));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(videoLesson);
        var updatedLesson = lessonService.update(lessonId, videoLesson);

        // then
        assertNotNull(updatedLesson);
        verify(lessonRepository).save(videoLesson);
    }

    @Test
    void deleteLesson_ShouldDeleteLesson() {
        // when
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(videoLesson));
        lessonService.delete(lessonId);

        // then
        verify(lessonRepository).delete(videoLesson);
    }

    @Test
    void deleteLesson_NotFound() {
        // when
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> lessonService.delete(lessonId));
    }

}
