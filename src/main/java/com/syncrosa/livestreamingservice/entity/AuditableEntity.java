package com.syncrosa.livestreamingservice.entity;

import com.syncrosa.livestreamingservice.converter.InstantToLongConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AuditableEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @Convert(converter = InstantToLongConverter.class)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    @Convert(converter = InstantToLongConverter.class)
    private Instant updatedAt;

}
