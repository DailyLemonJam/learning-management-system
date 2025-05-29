package com.leverx.learningmanagementsystem.lesson.repository;

import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
}
