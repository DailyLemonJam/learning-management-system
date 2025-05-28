package com.leverx.learningmanagementsystem.controller;

import com.leverx.learningmanagementsystem.dto.lesson.CreateLessonRequestDto;
import com.leverx.learningmanagementsystem.dto.lesson.LessonResponseDto;
import com.leverx.learningmanagementsystem.dto.lesson.UpdateLessonRequestDto;
import com.leverx.learningmanagementsystem.mapper.LessonMapper;
import com.leverx.learningmanagementsystem.service.lesson.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
        var result = lessonService.create(lesson, request.courseId());
        return lessonMapper.toDto(result);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Get Lesson", description = "Returns Lesson information")
    public LessonResponseDto get(@PathVariable UUID id) {
        return lessonMapper.toDto(lessonService.get(id));
    }

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Get Lessons", description = "Returns all Lessons")
    public List<LessonResponseDto> get() {
        return lessonMapper.toDtos(lessonService.get());
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update Lesson", description = "Updates Lesson information")
    public LessonResponseDto update(@PathVariable UUID id,
                                    @Valid @RequestBody UpdateLessonRequestDto request) {
        var lesson = lessonMapper.toModel(request);
        var result = lessonService.update(id, lesson);
        return lessonMapper.toDto(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Lesson", description = "Deletes Lesson entity from DB")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        lessonService.delete(id);
    }

}
