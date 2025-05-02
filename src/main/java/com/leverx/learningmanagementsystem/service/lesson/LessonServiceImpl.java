package com.leverx.learningmanagementsystem.service.lesson;

import com.leverx.learningmanagementsystem.dto.lesson.CreateLessonRequest;
import com.leverx.learningmanagementsystem.dto.lesson.LessonDto;
import com.leverx.learningmanagementsystem.dto.lesson.UpdateLessonRequest;
import com.leverx.learningmanagementsystem.exception.EntityNotFoundException;
import com.leverx.learningmanagementsystem.mapper.LessonMapper;
import com.leverx.learningmanagementsystem.model.Course;
import com.leverx.learningmanagementsystem.model.Lesson;
import com.leverx.learningmanagementsystem.repository.CourseRepository;
import com.leverx.learningmanagementsystem.repository.LessonRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;

    @Transactional
    @Override
    public LessonDto create(CreateLessonRequest request) {
        var course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        var createdLesson = saveLesson(course, request);
        return lessonMapper.toDto(createdLesson);
    }

    @Transactional(readOnly = true)
    @Override
    public LessonDto get(UUID lessonId) {
        var lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find lesson with id: " + lessonId));
        return lessonMapper.toDto(lesson);
    }

    @Transactional(readOnly = true)
    @Override
    public List<LessonDto> get() {
        var lessons = lessonRepository.findAll();
        return lessonMapper.toDto(lessons);
    }

    @Transactional
    @Override
    public LessonDto update(UUID lessonId, UpdateLessonRequest request) {
        var lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find lesson with id: " + lessonId));
        var updatedLesson = updateLesson(lesson, request);
        return lessonMapper.toDto(updatedLesson);
    }

    @Transactional
    @Override
    public void delete(UUID lessonId) {
        var lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find lesson with id: " + lessonId));
        lessonRepository.delete(lesson);
    }

    private Lesson saveLesson(Course course, CreateLessonRequest request) {
        var lesson = Lesson.builder()
                .title(request.title())
                .duration(request.duration())
                .course(course)
                .build();
        return lessonRepository.save(lesson);
    }

    private Lesson updateLesson(Lesson lesson, UpdateLessonRequest request) {
        lesson.setTitle(request.newTitle());
        lesson.setDuration(request.newDuration());
        return lessonRepository.save(lesson);
    }

}
