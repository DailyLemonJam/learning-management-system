package com.leverx.learningmanagementsystem.course.service.settings;

import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import com.leverx.learningmanagementsystem.course.repository.CourseSettingsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseSettingsServiceImpl implements CourseSettingsService {
    private final CourseSettingsRepository courseSettingsRepository;

    @Override
    @Transactional(readOnly = true)
    public CourseSettings get(UUID id) {
        return courseSettingsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course settings with id: " + id));
    }

    @Transactional
    @Override
    public CourseSettings update(UUID id, CourseSettings courseSettings) {
        var existingCourseSettings = courseSettingsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course settings with id: " + id));
        return updateCourseSettings(existingCourseSettings, courseSettings);
    }

    private CourseSettings updateCourseSettings(CourseSettings existingCourseSettings, CourseSettings courseSettings) {
        existingCourseSettings.setStartDate(courseSettings.getStartDate());
        existingCourseSettings.setEndDate(courseSettings.getEndDate());
        existingCourseSettings.setIsPublic(courseSettings.getIsPublic());
        return courseSettingsRepository.save(existingCourseSettings);
    }
    
}
