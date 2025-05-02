package com.leverx.learningmanagementsystem.service.course.settings;

import com.leverx.learningmanagementsystem.dto.course.settings.CourseSettingsDto;
import com.leverx.learningmanagementsystem.dto.course.settings.UpdateCourseSettingsRequest;

import java.util.UUID;

public interface CourseSettingsService {

    CourseSettingsDto get(UUID id);

    CourseSettingsDto update(UUID id, UpdateCourseSettingsRequest request);

}
