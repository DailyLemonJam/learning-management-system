package com.leverx.learningmanagementsystem.lesson.model;

import com.leverx.learningmanagementsystem.audit.model.AuditableEntity;
import com.leverx.learningmanagementsystem.course.model.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "lesson_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Lesson extends AuditableEntity {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
