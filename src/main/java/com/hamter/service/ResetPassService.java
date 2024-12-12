package com.hamter.service;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hamter.model.PassResetToken;
import com.hamter.model.User;
import com.hamter.repository.PassResetTokenRepository;
import com.hamter.repository.UserRepository;

@Service
public class ResetPassService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PassResetTokenRepository passResetTokenRepository;

    public ResetPassService(PasswordEncoder passwordEncoder, UserRepository userRepository, PassResetTokenRepository passResetTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.passResetTokenRepository = passResetTokenRepository;
    }

    public String changeResetPassword(String token, String newPassword) {
        PassResetToken resetToken = passResetTokenRepository.findByToken(token).orElse(null);
        if (resetToken == null) {
            return "Token không hợp lệ";
        }

        if (resetToken.getExpiryDate().before(new Date())) {
            return "Token đã hết hạn";
        }

        User user = resetToken.getUser();

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return "Mật khẩu đã được thay đổi thành công";
    }
}

