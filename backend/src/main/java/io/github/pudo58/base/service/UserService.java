package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.UserRegisterRequest;
import io.github.pudo58.record.AlertResponseRecord;
import io.github.pudo58.record.UserRecord;
import io.github.pudo58.record.UserRegisterRecord;

import java.security.NoSuchAlgorithmException;

public interface UserService extends Service<User> {
    User findByUsername(String username);

    UserRegisterRecord register(UserRegisterRequest model) throws NoSuchAlgorithmException;

    AlertResponseRecord verifyEmail(String email, String otp);

    UserRecord get();
}
