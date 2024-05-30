package io.github.pudo58.util;

import io.github.pudo58.base.entity.EmailOtp;
import io.github.pudo58.base.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
public class EmailSender {
    private final JavaMailSender javaMailSender;
    @Value("classpath:static/email_template/register_temp.html")
    private Resource emailOtpTemplate;
    @Value("classpath:static/email_template/email_reminder.html")
    private Resource emailReminderTemplate;
    private final long expiryTime;
    private final String frontEndUrl;

    public EmailSender(JavaMailSender javaMailSender, @Value("${verify.email.expiryTime}") long expiryTime, @Value("${frontend.url}") String frontEndUrl) {
        this.javaMailSender = javaMailSender;
        this.expiryTime = expiryTime;
        this.frontEndUrl = frontEndUrl;
    }

    public void sendOtp(EmailOtp emailOtp) throws IOException, MessagingException {
        String minus = String.valueOf((int) (expiryTime / 60000));
        File file = emailOtpTemplate.getFile();
        String content = Files.readString(file.toPath());
        content = content.replace("{{otp}}", emailOtp.getOtp());
        content = content.replace("{{minus}}", minus);
        MimeMessageHelper helper = new MimeMessageHelper(javaMailSender.createMimeMessage(), StandardCharsets.UTF_8.name());
        helper.setTo(emailOtp.getEmail());
        helper.setSubject("Xác thực email");
        helper.setText(content, true);
        javaMailSender.send(helper.getMimeMessage());
    }

    // gửi mail nhắc nhở người dùng xác thực email
    public void sendReminder(User user) throws MessagingException, IOException {
        File file = emailReminderTemplate.getFile();
        String content = Files.readString(file.toPath());
        content = content.replace("{{username}}", user.getUsername());
        content = content.replace("{{email}}", user.getEmail());
        content = content.replace("{{link}}", frontEndUrl.concat("/verifyEmail"));
        MimeMessageHelper helper = new MimeMessageHelper(javaMailSender.createMimeMessage(), StandardCharsets.UTF_8.name());
        helper.setTo(user.getEmail());
        helper.setSubject("Nhắc nhở xác thực email");
        helper.setText(content, true);
        javaMailSender.send(helper.getMimeMessage());
    }
}
