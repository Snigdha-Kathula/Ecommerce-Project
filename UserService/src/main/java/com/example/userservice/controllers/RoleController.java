package com.example.userservice.controllers;

import com.example.userservice.dtos.RoleRequestDto;
import com.example.userservice.exceptions.RoleAlreadyExistException;
import com.example.userservice.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleRequestDto> createRole(@RequestBody RoleRequestDto roleRequestDto) throws RoleAlreadyExistException {
        RoleRequestDto savedRoleDto = roleService.createRole(roleRequestDto.getRoleName());
        return new ResponseEntity<>(savedRoleDto, HttpStatus.CREATED);
    }
}
