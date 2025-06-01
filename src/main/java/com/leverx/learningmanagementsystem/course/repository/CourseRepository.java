package com.leverx.learningmanagementsystem.course.repository;

import com.leverx.learningmanagementsystem.course.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findAllByCourseSettings_StartDateBetween(LocalDateTime tomorrow, LocalDateTime afterTomorrow);

    @EntityGraph(attributePaths = {"lessons"})
    Page<Course> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"lessons"})
    Optional<Course> findById(UUID id);

    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.lessons WHERE c.id=:id")
    Optional<Course> findByIdForEnrollment(UUID id);

}
