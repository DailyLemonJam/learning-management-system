package com.leverx.learningmanagementsystem.lesson.service;

import com.leverx.learningmanagementsystem.lesson.model.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    Lesson create(Lesson lesson, UUID courseId);

    Lesson get(UUID lessonId);

    List<Lesson> get();

    Lesson update(UUID lessonId, Lesson lesson);

    void delete(UUID lessonId);

}
