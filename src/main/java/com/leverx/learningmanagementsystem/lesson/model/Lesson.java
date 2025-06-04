package com.leverx.learningmanagementsystem.lesson.model;

import com.leverx.learningmanagementsystem.core.audit.model.AuditableEntity;
import com.leverx.learningmanagementsystem.course.model.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

import static jakarta.persistence.InheritanceType.JOINED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lesson")
@Inheritance(strategy = JOINED)
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
