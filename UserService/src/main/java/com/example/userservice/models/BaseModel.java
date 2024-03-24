package com.example.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name ="uuidgenerator", strategy = "uuid")
    @Column(name = "id", columnDefinition = "binary(16)", nullable = false, updatable = false)
    private UUID id;
    @CreatedDate
    @Temporal(value=TemporalType.TIMESTAMP)
    @JsonIgnore
    private Instant createdAt;
    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonIgnore
    private Instant modifiedAt;
}
