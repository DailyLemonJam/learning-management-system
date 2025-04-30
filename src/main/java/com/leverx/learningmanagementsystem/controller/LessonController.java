package com.leverx.learningmanagementsystem.controller;

import com.leverx.learningmanagementsystem.dto.lesson.CreateLessonRequest;
import com.leverx.learningmanagementsystem.dto.lesson.LessonDto;
import com.leverx.learningmanagementsystem.dto.lesson.UpdateLessonRequest;
import com.leverx.learningmanagementsystem.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto createLesson(@RequestBody CreateLessonRequest request) {
        return lessonService.createLesson(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDto getLesson(@PathVariable UUID id) {
        return lessonService.getLesson(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LessonDto> getLessons() {
        return lessonService.getLessons();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDto updateLesson(@PathVariable UUID id,
                                  @RequestBody UpdateLessonRequest request) {
        return lessonService.updateLesson(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable UUID id) {
        lessonService.deleteLesson(id);
    }

}
