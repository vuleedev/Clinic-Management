package com.hamter.rest;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.model.PassResetToken;
import com.hamter.model.User;
import com.hamter.repository.PassResetTokenRepository;
import com.hamter.repository.UserRepository;

@RestController
@RequestMapping("/api/resetpass")
public class PassResetRestController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PassResetTokenRepository passResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/password-reset")
    public String resetPassword(@RequestParam String email) {

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "Email không hợp lệ";
        }

        String token = UUID.randomUUID().toString();

        PassResetToken resetToken = new PassResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 600000)); //10p
        passResetTokenRepository.save(resetToken);

        String resetUrl = "http://localhost:8080/reset-password?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Yêu cầu đặt lại mật khẩu");
        message.setText("Nhấp vào link để đổi mật khẩu: " + resetUrl);
        mailSender.send(message);

        return "Email đã được gửi";
    }
}

