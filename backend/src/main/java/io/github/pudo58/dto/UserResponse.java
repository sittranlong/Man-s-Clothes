package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private String username;
    private String email;
    private String role;
    private int status;
    private int balance;
    private String avatar;
    private byte[] avatarBytes;
}
