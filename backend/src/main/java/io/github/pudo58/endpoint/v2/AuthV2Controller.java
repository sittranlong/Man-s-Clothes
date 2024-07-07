package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.EmailOtp;
import io.github.pudo58.base.entity.Role;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.service.JwtService;
import io.github.pudo58.base.service.TokenService;
import io.github.pudo58.base.service.UserService;
import io.github.pudo58.dto.AuthRequest;
import io.github.pudo58.dto.UserRegisterRequest;
import io.github.pudo58.record.AlertResponseRecord;
import io.github.pudo58.record.TokenRecord;
import io.github.pudo58.record.UserRegisterRecord;
import io.github.pudo58.util.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class AuthV2Controller {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;
    private final HttpServletRequest request;
    private final Message message;
    @Value("classpath:demo.mkv")
    private Resource resource;

    @PostMapping("/login")
    public TokenRecord authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Assert.notNull(authRequest.getUsername(), message.getMessage("authentication.username.required"));
            Assert.notNull(authRequest.getPassword(), message.getMessage("authentication.password.required"));
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();
                Set<String> roleList = user.getRoleList().stream().map(Role::getName).collect(Collectors.toSet());
                String username = authRequest.getUsername();
                boolean rememberMe = Boolean.TRUE.equals(authRequest.getRememberMe());
                String token = rememberMe ? jwtService.generateRefreshToken(username) : jwtService.generateToken(username);
                tokenService.saveToken(username, token);
                return new TokenRecord(token, username, roleList, rememberMe);
            } else {
                throw new IllegalArgumentException(message.getMessage("authentication.authenticated.fail"));
            }
        } catch (InternalAuthenticationServiceException | BadCredentialsException ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(message.getMessage("authentication.information.incorrect"));
        }
    }

    @PostMapping("/register")
    public UserRegisterRecord register(@NonNull @RequestBody UserRegisterRequest model) throws NoSuchAlgorithmException {
        return userService.register(model);
    }

    @PostMapping("/verifyEmail")
    public AlertResponseRecord verifyEmail(@RequestBody EmailOtp emailOtp) {
        return userService.verifyEmail(emailOtp.getEmail(), emailOtp.getOtp());
    }

    @PostMapping("/logout")
    public AlertResponseRecord logout() {
        String username = User.getContext().getUsername();
        tokenService.deleteTokens(username);
        return new AlertResponseRecord(message.getMessage("authentication.logout.success"), HttpStatus.OK.value());
    }

    @PostMapping("/refresh")
    public TokenRecord refreshToken() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ") && token.length() > 7) {
            token = token.substring(7);
            String username = jwtService.extractUsername(token);
            if (username != null && jwtService.validateToken(token, userService.findByUsername(username))) {
                String newToken = jwtService.generateToken(username);
                tokenService.saveToken(username, newToken);
                return new TokenRecord(newToken, username, null, false);
            } else {
                throw new RuntimeException(message.getMessage("authentication.token.invalid"));
            }
        } else {
            throw new RuntimeException(message.getMessage("authentication.not-login"));
        }
    }
}
