package com.leverx.learningmanagementsystem.lesson.service;

import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import com.leverx.learningmanagementsystem.lesson.repository.LessonRepository;
import com.leverx.learningmanagementsystem.util.CourseUtil;
import com.leverx.learningmanagementsystem.util.LessonUtil;
import jakarta.persistence.EntityNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    void createLesson_ShouldSaveLesson() {
        // given
        var course = CourseUtil.createCourse();
        var courseId = UUID.randomUUID();
        var videoLesson = LessonUtil.createVideoLesson(course);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lessonRepository.save(videoLesson)).thenReturn(videoLesson);

        // when
        var createdLesson = lessonService.create(videoLesson, courseId);

        // then
        assertNotNull(createdLesson);
        assertEquals(course, createdLesson.getCourse());
        verify(lessonRepository).save(videoLesson);
    }

    @Test
    void getLesson_ShouldReturnLesson() {
        // given
        var course = CourseUtil.createCourse();
        var videoLesson = LessonUtil.createVideoLesson(course);
        var lessonId = UUID.randomUUID();
        videoLesson.setId(lessonId);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(videoLesson));

        // when
        var foundLesson = lessonService.get(lessonId);

        // then
        assertNotNull(foundLesson);
        assertEquals(lessonId, foundLesson.getId());
    }

    @Test
    void getLesson_ShouldThrowException() {
        // given
        var lessonId = UUID.randomUUID();
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> lessonService.get(lessonId));
    }

    @Test
    void getAllLessons_ShouldReturnLessonsPage() {
        // given
        var course = CourseUtil.createCourse();
        var videoLesson = LessonUtil.createVideoLesson(course);
        var lessonPage = new PageImpl<>(Collections.singletonList(videoLesson));
        when(lessonRepository.findAll(any(Pageable.class))).thenReturn(lessonPage);

        // when
        var result = lessonService.getAll(Pageable.unpaged());

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void updateLesson_ShouldUpdateLesson() {
        // given
        var course = CourseUtil.createCourse();
        var videoLesson = LessonUtil.createVideoLesson(course);
        var lessonId = UUID.randomUUID();
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(videoLesson));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(videoLesson);

        // when
        var updatedLesson = lessonService.update(lessonId, videoLesson);

        // then
        assertNotNull(updatedLesson);
        verify(lessonRepository).save(videoLesson);
    }

    @Test
    void deleteLesson_ShouldDeleteLesson() {
        // given
        var course = CourseUtil.createCourse();
        var videoLesson = LessonUtil.createVideoLesson(course);
        var lessonId = UUID.randomUUID();
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(videoLesson));

        // when
        lessonService.delete(lessonId);

        // then
        verify(lessonRepository).delete(videoLesson);
    }

    @Test
    void deleteLesson_NotFound() {
        // given
        var lessonId = UUID.randomUUID();
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> lessonService.delete(lessonId));
    }
}
