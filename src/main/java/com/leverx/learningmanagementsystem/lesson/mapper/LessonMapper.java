package com.leverx.learningmanagementsystem.lesson.mapper;

import com.leverx.learningmanagementsystem.lesson.dto.CreateLessonRequestDto;
import com.leverx.learningmanagementsystem.lesson.dto.LessonResponseDto;
import com.leverx.learningmanagementsystem.lesson.dto.UpdateLessonRequestDto;
import com.leverx.learningmanagementsystem.lesson.model.ClassroomLesson;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import com.leverx.learningmanagementsystem.lesson.model.LessonType;
import com.leverx.learningmanagementsystem.lesson.model.VideoLesson;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LessonMapper {

    public LessonResponseDto toDto(Lesson lesson) {
        if (lesson instanceof ClassroomLesson lessonCast) {
            return new LessonResponseDto(
                    lessonCast.getId(),
                    lessonCast.getTitle(),
                    lessonCast.getDuration(),
                    LessonType.CLASSROOM.getName(),
                    lessonCast.getLocation(),
                    lessonCast.getCapacity(),
                    null,
                    null,
                    lessonCast.getCourse().getId()
            );
        } else if (lesson instanceof VideoLesson lessonCast) {
            return new LessonResponseDto(
                    lessonCast.getId(),
                    lessonCast.getTitle(),
                    lessonCast.getDuration(),
                    LessonType.VIDEO.getName(),
                    null,
                    null,
                    lessonCast.getUrl(),
                    lessonCast.getPlatform(),
                    lessonCast.getCourse().getId()
            );
        }
        throw new RuntimeException("Probably new Lesson type was created");
    }

    public Page<LessonResponseDto> toDtos(Page<Lesson> lessons) {
        return lessons.map(this::toDto);
    }

    public Lesson toModel(CreateLessonRequestDto request) {
        var lessonType = request.lessonType();
        if (Objects.equals(lessonType, LessonType.CLASSROOM.getName())) {
            return createClassroomLesson(request);
        } else if (Objects.equals(lessonType, LessonType.VIDEO.getName())) {
            return createVideoLesson(request);
        }
        throw new RuntimeException("Incorrect lesson type");
    }

    public Lesson toModel(UpdateLessonRequestDto request) {
        var lessonType = request.lessonType();
        if (Objects.equals(lessonType, LessonType.CLASSROOM.getName())) {
            return createClassroomLesson(request);
        } else if (Objects.equals(lessonType, LessonType.VIDEO.getName())) {
            return createVideoLesson(request);
        }
        throw new RuntimeException("Incorrect lesson type");
    }

    private ClassroomLesson createClassroomLesson(CreateLessonRequestDto request) {
        return ClassroomLesson.builder()
                .title(request.title())
                .duration(request.duration())
                .location(request.location())
                .capacity(request.capacity())
                .build();
    }

    private ClassroomLesson createClassroomLesson(UpdateLessonRequestDto request) {
        return ClassroomLesson.builder()
                .title(request.title())
                .duration(request.duration())
                .location(request.location())
                .capacity(request.capacity())
                .build();
    }

    private VideoLesson createVideoLesson(CreateLessonRequestDto request) {
        return VideoLesson.builder()
                .title(request.title())
                .duration(request.duration())
                .url(request.url())
                .platform(request.platform())
                .build();
    }

    private VideoLesson createVideoLesson(UpdateLessonRequestDto request) {
        return VideoLesson.builder()
                .title(request.title())
                .duration(request.duration())
                .url(request.url())
                .platform(request.platform())
                .build();
    }

}
