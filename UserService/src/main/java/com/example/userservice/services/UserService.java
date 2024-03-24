package com.example.userservice.services;

import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.InvalidRoleException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.repositories.RoleRepository;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserDetails(String userId) throws UserNotFoundException {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()-> new UserNotFoundException("User Doesn't Exist with provided Id"));
        UserDto userDto = UserDto.from(user);
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    public UserDto setUserRole(List<String> roleIds, String userId) throws UserNotFoundException, InvalidRoleException {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()-> new UserNotFoundException("User Doesn't Exist with provided Id"));
        Set<Role> userRoles = user.getRoles();
        List<Optional<Role>> optionalRoles = roleIds.stream().map(UUID::fromString).map(roleRepository::findById).toList();
        for(Optional<Role> optionalRole:optionalRoles){
            if(optionalRole.isEmpty()) throw new InvalidRoleException("Invalid Role ID ");
            userRoles.add(optionalRole.get());
        }
        userRepository.save(user);
        UserDto userDto = UserDto.from(user);
        userDto.setRoles(userRoles);
        return userDto;

    }
}
