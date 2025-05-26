package com.leverx.learningmanagementsystem.course.model;

import com.leverx.learningmanagementsystem.audit.model.Auditable;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import com.leverx.learningmanagementsystem.student.model.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.MERGE;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course extends Auditable {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "coins_paid")
    private BigDecimal coinsPaid;

    @OneToOne(mappedBy = "course", cascade = ALL)
    private CourseSettings courseSettings;

    @OneToMany(mappedBy = "course", cascade = ALL)
    private List<Lesson> lessons;

    @ManyToMany(mappedBy = "courses", cascade = {PERSIST, MERGE})
    private List<Student> students;

}
