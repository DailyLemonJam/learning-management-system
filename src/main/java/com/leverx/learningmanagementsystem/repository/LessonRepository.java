package com.leverx.learningmanagementsystem.repository;

import com.leverx.learningmanagementsystem.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
}
