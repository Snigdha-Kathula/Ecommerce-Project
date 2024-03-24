package dev.snigdha.productservice.security;

import dev.snigdha.productservice.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TokenValidator {
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    public TokenValidator(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public void validateToken(String token) throws NotFoundException {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<SessionStatus> sessionStatus = restTemplate.getForEntity("http://localhost:9000/auth/validate/{token}", SessionStatus.class, token);
        if(sessionStatus.getBody().equals(SessionStatus.ENDED)) throw new NotFoundException("Token is Invalid");


    }
}
