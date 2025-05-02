package com.leverx.learningmanagementsystem.service.course.settings;

import com.leverx.learningmanagementsystem.dto.course.settings.CourseSettingsDto;
import com.leverx.learningmanagementsystem.dto.course.settings.UpdateCourseSettingsRequest;
import com.leverx.learningmanagementsystem.exception.EntityNotFoundException;
import com.leverx.learningmanagementsystem.mapper.CourseSettingsMapper;
import com.leverx.learningmanagementsystem.model.CourseSettings;
import com.leverx.learningmanagementsystem.repository.CourseSettingsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseSettingsServiceImpl implements CourseSettingsService {
    private final CourseSettingsRepository courseSettingsRepository;
    private final CourseSettingsMapper courseSettingsMapper;

    @Override
    public CourseSettingsDto get(UUID id) {
        var courseSettings = courseSettingsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course settings with id: " + id));
        return courseSettingsMapper.toDto(courseSettings);
    }

    @Transactional
    @Override
    public CourseSettingsDto update(UUID id, UpdateCourseSettingsRequest request) {
        var courseSettings = courseSettingsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find course settings with id: " + id));
        var updatedCourseSettings = updateCourseSettings(courseSettings, request);
        return courseSettingsMapper.toDto(updatedCourseSettings);
    }

    private CourseSettings updateCourseSettings(CourseSettings settings, UpdateCourseSettingsRequest request) {
        settings.setStartDate(request.newStartDate());
        settings.setEndDate(request.newEndDate());
        settings.setIsPublic(request.newIsPublic());
        return courseSettingsRepository.save(settings);
    }
    
}
