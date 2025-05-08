package com.leverx.learningmanagementsystem.course.mapper;

import com.leverx.learningmanagementsystem.course.dto.settings.CourseSettingsResponseDto;
import com.leverx.learningmanagementsystem.course.dto.settings.UpdateCourseSettingsRequestDto;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import org.springframework.stereotype.Component;

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
