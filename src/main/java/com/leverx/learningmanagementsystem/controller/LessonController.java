package com.leverx.learningmanagementsystem.controller;

import com.leverx.learningmanagementsystem.dto.lesson.CreateLessonRequest;
import com.leverx.learningmanagementsystem.dto.lesson.LessonDto;
import com.leverx.learningmanagementsystem.dto.lesson.UpdateLessonRequest;
import com.leverx.learningmanagementsystem.service.lesson.LessonService;
import jakarta.validation.Valid;
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
    public LessonDto createLesson(@Valid @RequestBody CreateLessonRequest request) {
        return lessonService.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDto getLesson(@PathVariable UUID id) {
        return lessonService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LessonDto> getLessons() {
        return lessonService.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDto updateLesson(@PathVariable UUID id,
                                  @Valid @RequestBody UpdateLessonRequest request) {
        return lessonService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable UUID id) {
        lessonService.delete(id);
    }

}
