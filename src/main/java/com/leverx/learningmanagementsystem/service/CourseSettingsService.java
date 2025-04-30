package com.leverx.learningmanagementsystem.service;

import com.leverx.learningmanagementsystem.dto.course.settings.CourseSettingsDto;
import com.leverx.learningmanagementsystem.dto.course.settings.UpdateCourseSettingsRequest;
import com.leverx.learningmanagementsystem.exception.CourseSettingsNotFoundException;
import com.leverx.learningmanagementsystem.mapper.CourseSettingsMapper;
import com.leverx.learningmanagementsystem.repository.CourseSettingsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseSettingsService {
    private final CourseSettingsRepository courseSettingsRepository;
    private final CourseSettingsMapper courseSettingsMapper;

    public CourseSettingsDto getCourseSettings(UUID id) {
        var courseSettings = courseSettingsRepository.findById(id)
                .orElseThrow(() -> new CourseSettingsNotFoundException("Can't find course settings with id: " + id));
        return courseSettingsMapper.toDto(courseSettings);
    }

    @Transactional
    public CourseSettingsDto updateCourseSettings(UUID id, UpdateCourseSettingsRequest request) {
        var courseSettings = courseSettingsRepository.findById(id)
                .orElseThrow(() -> new CourseSettingsNotFoundException("Can't find course settings with id: " + id));
        courseSettings.setStartDate(request.newStartDate());
        courseSettings.setEndDate(request.newEndDate());
        courseSettings.setIsPublic(request.newIsPublic());
        var updatedCourseSettings = courseSettingsRepository.save(courseSettings);
        return courseSettingsMapper.toDto(updatedCourseSettings);
    }

}
