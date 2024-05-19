package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {
    private String username;
    private String password;
    private Boolean rememberMe;
}
