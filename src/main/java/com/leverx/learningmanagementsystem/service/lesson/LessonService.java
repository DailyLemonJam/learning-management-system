package com.leverx.learningmanagementsystem.service.lesson;

import com.leverx.learningmanagementsystem.dto.lesson.CreateLessonRequest;
import com.leverx.learningmanagementsystem.dto.lesson.LessonDto;
import com.leverx.learningmanagementsystem.dto.lesson.UpdateLessonRequest;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    LessonDto create(CreateLessonRequest request);

    LessonDto get(UUID lessonId);

    List<LessonDto> get();

    LessonDto update(UUID lessonId, UpdateLessonRequest request);

    void delete(UUID lessonId);

}
