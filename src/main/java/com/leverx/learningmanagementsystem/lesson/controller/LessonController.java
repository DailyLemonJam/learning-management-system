package com.leverx.learningmanagementsystem.lesson.controller;

import com.leverx.learningmanagementsystem.lesson.dto.CreateLessonRequestDto;
import com.leverx.learningmanagementsystem.lesson.dto.LessonResponseDto;
import com.leverx.learningmanagementsystem.lesson.dto.UpdateLessonRequestDto;
import com.leverx.learningmanagementsystem.lesson.mapper.LessonMapper;
import com.leverx.learningmanagementsystem.lesson.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@Tag(name = "Lesson Controller", description = "Handles Lesson operations")
public class LessonController {

    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create Lesson", description = "Creates Lesson and connects it to specified Course")
    public LessonResponseDto create(@Valid @RequestBody CreateLessonRequestDto request) {
        var lesson = lessonMapper.toModel(request);
        var createdLesson = lessonService.create(lesson, request.courseId());
        return lessonMapper.toDto(createdLesson);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Get Lesson", description = "Returns Lesson information")
    public LessonResponseDto get(@PathVariable UUID id) {
        var lesson = lessonService.get(id);
        return lessonMapper.toDto(lesson);
    }

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Get Lessons", description = "Returns all Lessons")
    public PagedModel<LessonResponseDto> getAll(Pageable pageable) {
        var lessons = lessonService.getAll(pageable);
        return lessonMapper.toDtos(lessons);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update Lesson", description = "Updates Lesson information")
    public LessonResponseDto update(@PathVariable UUID id,
                                    @Valid @RequestBody UpdateLessonRequestDto request) {
        var lesson = lessonMapper.toModel(request);
        var updatedLesson = lessonService.update(id, lesson);
        return lessonMapper.toDto(updatedLesson);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Lesson", description = "Deletes Lesson entity from DB")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        lessonService.delete(id);
    }
}
