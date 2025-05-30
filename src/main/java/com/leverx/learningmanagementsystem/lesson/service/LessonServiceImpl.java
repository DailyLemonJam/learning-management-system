package com.leverx.learningmanagementsystem.lesson.service;

import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.lesson.model.ClassroomLesson;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import com.leverx.learningmanagementsystem.lesson.model.VideoLesson;
import com.leverx.learningmanagementsystem.lesson.repository.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Lesson get(UUID lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find lesson with id: " + lessonId));
    }

    @Override
    public Page<Lesson> getAll(Pageable pageable) {
        return lessonRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Lesson update(UUID lessonId, Lesson updatedLesson) {
        var existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find lesson with id: " + lessonId));
        return updateLesson(existingLesson, updatedLesson);
    }

    @Transactional
    @Override
    public void delete(UUID lessonId) {
        var lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find lesson with id: " + lessonId));
        lessonRepository.delete(lesson);
    }

    private Lesson updateLesson(Lesson existingLesson, Lesson updatedLesson) {
        if (existingLesson.getClass().equals(updatedLesson.getClass())) {
            if (existingLesson instanceof ClassroomLesson) {
                return updateAsClassroomLesson(existingLesson, updatedLesson);
            } else if (existingLesson instanceof VideoLesson) {
                return updateAsVideoLesson(existingLesson, updatedLesson);
            }
            throw new RuntimeException("Unknown Lesson type");
        }
        updatedLesson.setCourse(existingLesson.getCourse());
        lessonRepository.delete(existingLesson);
        return lessonRepository.save(updatedLesson);
    }

    private Lesson updateAsClassroomLesson(Lesson existingLesson, Lesson updatedLesson) {
        var existingLessonCast = (ClassroomLesson) existingLesson;
        var updatedLessonCast = (ClassroomLesson) updatedLesson;
        existingLessonCast.setTitle(updatedLessonCast.getTitle());
        existingLessonCast.setDuration(updatedLessonCast.getDuration());
        existingLessonCast.setLocation(updatedLessonCast.getLocation());
        existingLessonCast.setCapacity(updatedLessonCast.getCapacity());
        return lessonRepository.save(existingLessonCast);
    }

    private Lesson updateAsVideoLesson(Lesson existingLesson, Lesson updatedLesson) {
        var existingLessonCast = (VideoLesson) existingLesson;
        var updatedLessonCast = (VideoLesson) updatedLesson;
        existingLessonCast.setTitle(updatedLessonCast.getTitle());
        existingLessonCast.setDuration(updatedLessonCast.getDuration());
        existingLessonCast.setUrl(updatedLessonCast.getUrl());
        existingLessonCast.setPlatform(updatedLessonCast.getPlatform());
        return lessonRepository.save(existingLessonCast);
    }

}
