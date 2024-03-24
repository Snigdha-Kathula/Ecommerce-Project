package com.example.userservice.controllers;

import com.example.userservice.dtos.SetUserRoleRequestDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.InvalidRoleException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") String userId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserDetails(userId));
    }
    @PostMapping("{id}/roles")
    public ResponseEntity<UserDto> setUserRole(@RequestBody SetUserRoleRequestDto setUserRoleRequestDto, @PathVariable("id") String userId) throws UserNotFoundException, InvalidRoleException {
        return ResponseEntity.ok(userService.setUserRole(setUserRoleRequestDto.getRoleIds(), userId));
    }
}
