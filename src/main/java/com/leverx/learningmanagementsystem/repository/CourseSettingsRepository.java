package com.leverx.learningmanagementsystem.repository;

import com.leverx.learningmanagementsystem.model.CourseSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseSettingsRepository extends JpaRepository<CourseSettings, UUID> {
}
