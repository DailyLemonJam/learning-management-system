package com.leverx.learningmanagementsystem.service;

import com.leverx.learningmanagementsystem.dto.lesson.CreateLessonRequest;
import com.leverx.learningmanagementsystem.dto.lesson.LessonDto;
import com.leverx.learningmanagementsystem.dto.lesson.UpdateLessonRequest;
import com.leverx.learningmanagementsystem.exception.CourseNotFoundException;
import com.leverx.learningmanagementsystem.exception.LessonNotFoundException;
import com.leverx.learningmanagementsystem.mapper.LessonMapper;
import com.leverx.learningmanagementsystem.model.Lesson;
import com.leverx.learningmanagementsystem.repository.CourseRepository;
import com.leverx.learningmanagementsystem.repository.LessonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Transactional
    public LessonDto createLesson(CreateLessonRequest request) {
        var course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));
        var lesson = Lesson.builder()
                .title(request.title())
                .duration(request.duration())
                .course(course)
                .build();
        var createdLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(createdLesson);
    }

    public LessonDto getLesson(UUID lessonId) {
        var lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Can't find lesson with id: " + lessonId));
        return lessonMapper.toDto(lesson);
    }

    public List<LessonDto> getLessons() {
        var lessons = lessonRepository.findAll();
        return lessonMapper.toDto(lessons);
    }

    @Transactional
    public LessonDto updateLesson(UUID lessonId, UpdateLessonRequest request) {
        var lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Can't find lesson with id: " + lessonId));
        lesson.setTitle(request.newTitle());
        lesson.setDuration(request.newDuration());
        var updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(updatedLesson);
    }

    @Transactional
    public void deleteLesson(UUID lessonId) {
        var lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Can't find lesson with id: " + lessonId));
        lessonRepository.delete(lesson);
    }

}
