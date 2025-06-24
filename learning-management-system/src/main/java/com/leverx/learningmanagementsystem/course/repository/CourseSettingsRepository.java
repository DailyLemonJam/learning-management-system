package com.leverx.learningmanagementsystem.course.repository;

import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseSettingsRepository extends JpaRepository<CourseSettings, UUID> {
}
