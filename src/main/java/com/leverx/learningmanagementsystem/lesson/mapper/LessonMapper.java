package com.leverx.learningmanagementsystem.lesson.mapper;

import com.leverx.learningmanagementsystem.lesson.dto.CreateLessonRequestDto;
import com.leverx.learningmanagementsystem.lesson.dto.LessonResponseDto;
import com.leverx.learningmanagementsystem.lesson.dto.UpdateLessonRequestDto;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonMapper {

    public LessonResponseDto toDto(Lesson lesson) {
        return new LessonResponseDto(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDuration(),
                lesson.getCourse().getId());
    }

    public List<LessonResponseDto> toDtos(List<Lesson> lessons) {
        var dtos = new ArrayList<LessonResponseDto>();
        lessons.forEach(lesson -> dtos.add(toDto(lesson)));
        return dtos;
    }

    public Lesson toModel(CreateLessonRequestDto request) {
        return Lesson.builder()
                .title(request.title())
                .duration(request.duration())
                .build();
    }

    public Lesson toModel(UpdateLessonRequestDto request) {
        return Lesson.builder()
                .title(request.title())
                .duration(request.duration())
                .build();
    }

}
