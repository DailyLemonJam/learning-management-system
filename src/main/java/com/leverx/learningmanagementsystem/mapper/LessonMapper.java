package com.leverx.learningmanagementsystem.mapper;

import com.leverx.learningmanagementsystem.dto.lesson.LessonDto;
import com.leverx.learningmanagementsystem.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonMapper implements DtoMapper<Lesson, LessonDto> {

    @Override
    public LessonDto toDto(Lesson lesson) {
        return new LessonDto(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDuration(),
                lesson.getCourse().getId());
    }

    @Override
    public List<LessonDto> toDto(List<Lesson> lessons) {
        var dtos = new ArrayList<LessonDto>();
        for (var lesson : lessons) {
            dtos.add(toDto(lesson));
        }
        return dtos;
    }

}
