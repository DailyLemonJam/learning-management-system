package com.leverx.learningmanagementsystem.audit.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedDate
    @Column(updatable = false, name = "created")
    private LocalDateTime created;

    @CreatedBy
    @Column(updatable = false, name = "created_by")
    private String createdBy;

    @LastModifiedDate
    @Column(name = "last_changed")
    private LocalDateTime lastChanged;

    @LastModifiedBy
    @Column(name = "last_changed_by")
    private String lastChangedBy;

}
