package com.example.userservice.repositories;

import com.example.userservice.models.Session;
import com.example.userservice.models.User;
import org.antlr.v4.runtime.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findSessionByTokenAndUser( String token, User user);
    Optional<Session> findSessionByToken(String token);
}
