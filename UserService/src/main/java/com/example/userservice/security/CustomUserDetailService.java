package com.example.userservice.security;

import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@JsonDeserialize(as = CustomUserDetailService.class)
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @JsonIgnore
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(userOptional.isEmpty()) throw new UsernameNotFoundException("User does not exist");
        User user = userOptional.get();
        return new CustomUserDetails(user);
    }
}
