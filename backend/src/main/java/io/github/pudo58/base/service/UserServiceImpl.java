package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.EmailOtp;
import io.github.pudo58.base.entity.Role;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.exception.UnauthorizedException;
import io.github.pudo58.base.repo.EmailOtpRepo;
import io.github.pudo58.base.repo.RoleRepo;
import io.github.pudo58.base.repo.UserRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.constant.UserConst;
import io.github.pudo58.dto.UserRegisterRequest;
import io.github.pudo58.record.AlertResponseRecord;
import io.github.pudo58.record.UserRecord;
import io.github.pudo58.record.UserRegisterRecord;
import io.github.pudo58.util.EmailSender;
import io.github.pudo58.util.Message;
import io.github.pudo58.util.Random;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractService<User> implements UserService {
    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final EmailOtpRepo emailOtpRepo;
    private final EmailSender emailSender;
    private final Message message;
    @Value("${verify.email.expiryTime}")
    private long expiryTime;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public User save(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return super.save(user);
    }


    @Override
    public User findByUsername(String username) {
        return this.repo.findByUsername(username);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public UserRegisterRecord register(UserRegisterRequest model) {
        User user = new User();
        Assert.hasText(model.getUsername(), () -> message.getMessage("authentication.username.required"));
        Assert.hasText(model.getPassword(), () -> message.getMessage("authentication.password.required"));
        Assert.hasText(model.getEmail(), () -> message.getMessage("user.email.required"));
        Assert.hasText(model.getConfirmPassword(), () -> message.getMessage("user.confirm-password.required"));
        Assert.isTrue(model.getPassword().equals(model.getConfirmPassword()), () -> message.getMessage("user.confirm-password.invalid"));
        user.setEmail(model.getEmail());
        user.setUsername(model.getUsername());
        user.setPassword(this.passwordEncoder.encode(model.getPassword()));
        this.addRole(user);
        this.repo.save(user);
        this.sendEmailOtp(user);
        return new UserRegisterRecord(HttpStatus.OK.value(), message.getMessage("user.register.success"));

    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public AlertResponseRecord verifyEmail(String email, String otp) {
        List<EmailOtp> emailOtpList = emailOtpRepo.findByEmailOrderByCreateDateDesc(email);
        if (emailOtpList.isEmpty()) {
            throw new IllegalArgumentException(message.getMessage("user.email.not-exist"));
        }
        EmailOtp emailOtp = emailOtpList.get(0);
        if (emailOtp.isVerified()) {
            throw new IllegalArgumentException(message.getMessage("user.email.verified", email));
        }
        if (emailOtp.getExpiryDate().before(new Date())) {
            throw new IllegalArgumentException(message.getMessage("user.email.authentication-code.expired"));
        }
        if (!emailOtp.getOtp().equals(otp)) {
            throw new IllegalArgumentException(message.getMessage("user.email.authentication-code.invalid"));
        }
        User user = emailOtp.getUser();
        user.setStatus(UserConst.STATUS_ACTIVE);
        user.setIsEnable(Boolean.TRUE);
        this.repo.save(user);
        emailOtp.setVerified(true);
        emailOtpRepo.save(emailOtp);
        return new AlertResponseRecord(message.getMessage("user.email.authentication-success"), HttpStatus.OK.value());
    }

    @Override
    public UserRecord get() {
        UUID id = User.getContextId();
        if (id == null) {
            throw new UnauthorizedException(message.getMessage("authentication.not-login"));
        }
        User user = this.repo.findById(id).orElse(null);
        if (user == null) {
            throw new UnauthorizedException(message.getMessage("authentication.not-login"));
        }
        return new UserRecord(user.getId(), user.getUsername(), user.getEmail(), user.getRoleList().stream().map(Role::getName).collect(Collectors.toSet()), user.getStatus());
    }

    private void addRole(User user) {
        try {
            Role role = roleRepo.findAll((root, query, builder) -> builder.equal(root.get("name"), UserConst.ROLE_ADMIN)).stream().findFirst().orElse(null);
            user.getRoleList().add(role);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void sendEmailOtp(User user) {
        String otp = String.valueOf(Random.randomNumber(100000, 999999));
        EmailOtp emailOtp = new EmailOtp();
        emailOtp.setOtp(otp);
        emailOtp.setUser(user);
        emailOtp.setEmail(user.getEmail());
        emailOtp.setExpiryDate(new Date(System.currentTimeMillis() + expiryTime));
        emailOtpRepo.save(emailOtp);
        ExecutorService executorService =
                new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>());
        executorService.submit(() -> {
            try {
                emailSender.sendOtp(emailOtp);
            } catch (IOException | MessagingException e) {
                throw new IllegalArgumentException(e);
            }
        });
        executorService.shutdown();
    }
}
