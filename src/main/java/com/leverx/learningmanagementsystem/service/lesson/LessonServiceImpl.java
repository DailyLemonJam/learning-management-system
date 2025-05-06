package com.leverx.learningmanagementsystem.service.lesson;

import com.leverx.learningmanagementsystem.model.Lesson;
import com.leverx.learningmanagementsystem.repository.CourseRepository;
import com.leverx.learningmanagementsystem.repository.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Transactional
    @Override
    public Lesson create(Lesson lesson, UUID courseId) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    @Transactional(readOnly = true)
    @Override
    public Lesson get(UUID lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find lesson with id: " + lessonId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Lesson> get() {
        return lessonRepository.findAll();
    }

    @Transactional
    @Override
    public Lesson update(UUID lessonId, Lesson lesson) {
        var existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find lesson with id: " + lessonId));
        return updateLesson(existingLesson, lesson);
    }

    @Transactional
    @Override
    public void delete(UUID lessonId) {
        var lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find lesson with id: " + lessonId));
        lessonRepository.delete(lesson);
    }

    private Lesson updateLesson(Lesson existingLesson, Lesson lesson) {
        existingLesson.setTitle(lesson.getTitle());
        existingLesson.setDuration(lesson.getDuration());
        return lessonRepository.save(existingLesson);
    }

}
