package dev.snigdha.productservice.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JwtData {
    private String id;
    private String email;
    private List<String> roles;
}
