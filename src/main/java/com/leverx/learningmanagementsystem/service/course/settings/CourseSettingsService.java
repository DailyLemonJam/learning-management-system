package com.leverx.learningmanagementsystem.service.course.settings;

import com.leverx.learningmanagementsystem.model.CourseSettings;

import java.util.UUID;

public interface CourseSettingsService {

    CourseSettings get(UUID id);

    CourseSettings update(UUID id, CourseSettings courseSettings);

}
