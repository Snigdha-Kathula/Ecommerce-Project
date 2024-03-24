package com.example.userservice.services;

import com.example.userservice.dtos.RoleRequestDto;
import com.example.userservice.exceptions.RoleAlreadyExistException;
import com.example.userservice.models.Role;
import com.example.userservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private  RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleRequestDto createRole(String roleName) throws RoleAlreadyExistException {
        Optional<Role> roleOptional = roleRepository.findByRole(roleName);
        if(roleOptional.isPresent()){
            throw new RoleAlreadyExistException("Role Already Exist");
        }
        Role role = new Role();
        role.setRole(roleName);
        Role savedRole = roleRepository.save(role);
        RoleRequestDto roleRequestDto = new RoleRequestDto();
        roleRequestDto.setRoleName(roleName);
        roleRequestDto.setId(savedRole.getId().toString());
        return roleRequestDto;
    }
}
