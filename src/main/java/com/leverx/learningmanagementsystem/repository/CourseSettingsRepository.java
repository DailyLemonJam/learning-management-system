package com.leverx.learningmanagementsystem.repository;

import com.leverx.learningmanagementsystem.model.CourseSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseSettingsRepository extends JpaRepository<CourseSettings, UUID> {
}
