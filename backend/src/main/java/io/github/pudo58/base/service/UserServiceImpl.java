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
import io.github.pudo58.util.Random;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl extends AbstractService<User> implements UserService {
    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final EmailOtpRepo emailOtpRepo;
    private final Environment env;
    private final JavaMailSender mailSender;

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
    public UserRegisterRecord register(UserRegisterRequest model) throws NoSuchAlgorithmException {
        User user = new User();
        Assert.notNull(model.getUsername(), () -> "Username is required");
        Assert.notNull(model.getPassword(), () -> "Password is required");
        user.setUsername(model.getUsername());
        user.setPassword(this.passwordEncoder.encode(model.getPassword()));
        this.addRole(user);
        this.repo.save(user);
        return new UserRegisterRecord(HttpStatus.OK.value(), "Đăng ký thành công");

    }

    @Override
    public UserRecord get() {
        UUID id = User.getContextId();
        if (id == null) {
            throw new UnauthorizedException("Bạn chưa đăng nhập");
        }
        User user = this.repo.findById(id).orElse(null);
        if (user == null) {
            throw new UnauthorizedException("Bạn chưa đăng nhập");
        }
        return new UserRecord(user.getId(), user.getUsername(), user.getEmail(), user.getRoleList().stream().map(Role::getName).collect(Collectors.toSet()), user.getStatus());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AlertResponseRecord sendOtp() {
        User user = User.getContext();
        Assert.notNull(user, "Bạn chưa đăng nhập");
        Assert.hasText(user.getEmail(), "Bạn chưa cập nhật email");
        List<EmailOtp> emailOtpList = this.emailOtpRepo.findByEmailOrderByCreateDateDesc(user.getEmail());
        if (!emailOtpList.isEmpty()) {
            // sau 1 phút mới gửi lại
            if (emailOtpList.get(0).getCreateDate().after(new Date(System.currentTimeMillis() - 60000L))) {
                throw new IllegalArgumentException("Bạn vui lòng đợi 1 phút để gửi lại mã OTP");
            }
            this.emailOtpRepo.deleteAll(emailOtpList);
        }
        EmailOtp emailOtp = new EmailOtp();
        emailOtp.setEmail(user.getEmail());
        emailOtp.setOtp(String.valueOf(Random.randomNumber(env.getProperty("verify.email.defaultLength", Integer.class, 6))));
        Date expiryDate = new Date(System.currentTimeMillis() + env.getProperty("verify.email.expiryTime", Long.class, 300000L));
        emailOtp.setCreateBy(user.getUsername());
        emailOtp.setExpiryDate(expiryDate);
        this.emailOtpRepo.save(emailOtp);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> this.sendMailOtp(emailOtp.getOtp(), emailOtp.getEmail()));
        return new AlertResponseRecord("Mã OTP đã được gửi đến email của bạn", HttpStatus.OK.value());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AlertResponseRecord verifyOtp(String otp) {
        Assert.hasText(otp, "Mã OTP không được để trống");
        User user = User.getContext();
        List<EmailOtp> emailOtpList = this.emailOtpRepo.findByEmailOrderByCreateDateDesc(user.getEmail());
        EmailOtp emailOtp = emailOtpList.get(0);
        Assert.isTrue(emailOtp.getOtp().equals(otp), "Mã OTP không chính xác");
        Assert.isTrue(!emailOtp.isVerified(), "Mã OTP đã được xác thực");
        Assert.isTrue(emailOtp.getExpiryDate().after(new Date()), "Mã OTP đã hết hạn");

        emailOtp.setVerified(Boolean.TRUE);
        this.emailOtpRepo.save(emailOtp);
        user.getRoleList().add(roleRepo.findByName(UserConst.ROLE_USER));
        this.repo.save(user);
        return new AlertResponseRecord("Xác thực email thành công", HttpStatus.OK.value());
    }

    @Override
    public AlertResponseRecord callbackFromPayOs(String code, String id, String cancel, String status, String orderCode) {
        return null;
    }

    private void addRole(User user) {
        try {
            Role role = roleRepo.findAll((root, query, builder) -> builder.equal(root.get("name"), UserConst.ROLE_GUEST)).stream().findFirst().orElse(null);
            user.getRoleList().add(role);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMailOtp(String otp, String email) {
        try {
            String htmlContent = "<div style=\"background-color: #f2f2f2; padding: 20px;\">\n" +
                    "    <div style=\"background-color: #fff; padding: 20px;\">\n" +
                    "        <h1 style=\"text-align: center; color: #ff0000;\">Mã OTP của bạn là: " + otp + "</h1>\n" +
                    "        <p style=\"text-align: center; color: #000000;\">Mã OTP này sẽ hết hạn trong vòng 5 phút</p>\n" +
                    "    </div>\n" +
                    "</div>";
            MimeMessageHelper message = new MimeMessageHelper(this.mailSender.createMimeMessage(), "UTF-8");
            message.setCc(email);
            message.setSubject("Xác thực email");
            message.setText(htmlContent, true);
            mailSender.send(message.getMimeMessage());
            mailSender.send(message.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
