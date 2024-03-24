package com.example.userservice.controllers;

import com.example.userservice.dtos.*;
import com.example.userservice.exceptions.PasswordMismatchException;
import com.example.userservice.exceptions.SessionNotFoundException;
import com.example.userservice.exceptions.UserAlreadyExistException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {

        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFoundException, PasswordMismatchException {
        return authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws UserNotFoundException, SessionNotFoundException {
        return authService.logout(logoutRequestDto.getUserId(), logoutRequestDto.getToken());
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) throws UserAlreadyExistException {
        return authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword());

    }
    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateRequestDto validateRequestDto) throws UserNotFoundException, SessionNotFoundException {
        return ResponseEntity.ok(authService.validate(validateRequestDto.getUserId(), validateRequestDto.getToken()));

    }
    @GetMapping("/validate/{token}")
    public ResponseEntity<SessionStatus> validate(@PathVariable String token) {
        return ResponseEntity.ok(authService.validate(token));
    }

}
