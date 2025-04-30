package com.leverx.learningmanagementsystem.mapper;

import com.leverx.learningmanagementsystem.dto.course.settings.CourseSettingsDto;
import com.leverx.learningmanagementsystem.model.CourseSettings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseSettingsMapper implements DtoMapper<CourseSettings, CourseSettingsDto> {

    @Override
    public CourseSettingsDto toDto(CourseSettings courseSettings) {
        return new CourseSettingsDto(
                courseSettings.getId(),
                courseSettings.getStartDate(),
                courseSettings.getEndDate(),
                courseSettings.getIsPublic()
        );
    }

    @Override
    public List<CourseSettingsDto> toDto(List<CourseSettings> courseSettings) {
        var courseSettingsDtos = new ArrayList<CourseSettingsDto>();
        for (var courseSetting : courseSettings) {
            courseSettingsDtos.add(toDto(courseSetting));
        }
        return courseSettingsDtos;
    }
}
