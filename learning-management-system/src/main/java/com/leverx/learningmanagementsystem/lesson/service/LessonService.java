package com.leverx.learningmanagementsystem.lesson.service;

import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LessonService {

    Lesson create(Lesson lesson, UUID courseId);

    Lesson get(UUID lessonId);

    Page<Lesson> getAll(Pageable pageable);

    Lesson update(UUID lessonId, Lesson lesson);

    void delete(UUID lessonId);
}
