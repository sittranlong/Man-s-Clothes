package io.github.pudo58.util;

import io.github.pudo58.base.entity.EmailOtp;
import io.github.pudo58.base.entity.Order;
import io.github.pudo58.base.entity.OrderDetail;
import io.github.pudo58.base.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

    @Value("classpath:static/email_template/order_alert.html")
    private Resource orderTemplate;
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

    public void sendOrderAlert(Order order) throws IOException, MessagingException {
        File file = orderTemplate.getFile();
        String content = Files.readString(file.toPath());
        content = content.replace("{{fullName}}", order.getUser().getFullName());
        StringBuilder orderDetails = new StringBuilder();
        for (OrderDetail detail : order.getOrderDetails()) {
            orderDetails.append("<tr>")
                    .append("<td>").append(detail.getProductDetail().getProduct().getName()).append("</td>")
                    .append("<td>").append(detail.getQuantity()).append("</td>")
                    .append("<td>").append(detail.getPrice()).append("</td>")
                    .append("<td>").append(detail.getTotal()).append("</td>")
                    .append("</tr>");
        }
        content = content.replace("{{orderDetails}}", orderDetails.toString());
        content = content.replace("{{finalTotal}}", String.valueOf(order.getFinalTotal()));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
        helper.setTo(order.getUser().getEmail());
        helper.setSubject("Xác nhận Đơn hàng");
        helper.setText(content, true);
        javaMailSender.send(helper.getMimeMessage());
    }


}
