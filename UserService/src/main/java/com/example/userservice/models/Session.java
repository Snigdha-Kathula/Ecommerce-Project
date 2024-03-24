package com.example.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@Entity
public class Session extends BaseModel{
    private String token;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date expiryAt;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}
