package com.syncrosa.livestreamingservice.entity;

import com.syncrosa.livestreamingservice.converter.InstantToLongConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "streams")
public class Stream extends AuditableEntity {

    @Id
    private String id;

    private String title;

    @Column(name = "full_name")
    private String fullName;

    private String token;

    @Column(name = "is_token_active")
    private Boolean isTokenActive;

    @Column(name = "is_stream_active")
    private Boolean isStreamActive;

    @Column(name = "expired_at")
    @Convert(converter = InstantToLongConverter.class)
    private Instant expiredAt;

}
