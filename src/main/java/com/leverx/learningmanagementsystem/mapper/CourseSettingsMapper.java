package com.leverx.learningmanagementsystem.mapper;

import com.leverx.learningmanagementsystem.dto.course.settings.CourseSettingsResponseDto;
import com.leverx.learningmanagementsystem.dto.course.settings.UpdateCourseSettingsRequestDto;
import com.leverx.learningmanagementsystem.model.CourseSettings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseSettingsMapper {

    public CourseSettingsResponseDto toDto(CourseSettings courseSettings) {
        return new CourseSettingsResponseDto(
                courseSettings.getId(),
                courseSettings.getStartDate(),
                courseSettings.getEndDate(),
                courseSettings.getIsPublic()
        );
    }

    public CourseSettings toModel(UpdateCourseSettingsRequestDto request) {
        return CourseSettings.builder()
                .startDate(request.startDate())
                .endDate(request.endDate())
                .isPublic(request.isPublic())
                .build();
    }

}
