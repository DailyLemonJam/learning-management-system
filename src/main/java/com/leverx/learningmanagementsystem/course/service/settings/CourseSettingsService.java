package com.leverx.learningmanagementsystem.course.service.settings;

import com.leverx.learningmanagementsystem.course.model.CourseSettings;

import java.util.UUID;

public interface CourseSettingsService {

    CourseSettings get(UUID id);

    CourseSettings update(UUID id, CourseSettings courseSettings);
}
