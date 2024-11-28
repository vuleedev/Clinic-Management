package com.hamter.service;

import org.springframework.stereotype.Service;

import com.hamter.model.Role;
import com.hamter.model.User;
import com.hamter.repository.UserRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
	
	@Autowired
	private RoleService roleService;
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }
        return user;
    }

    public void registerUser(String email, String password) {
        // Encode password
        String encodedPassword = passwordEncoder.encode(password);

        // Create new user and assign default role
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(encodedPassword);
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());

        // Assign the "CUST" role
        Role defaultRole = roleService.findByRoleName("CUST");
        if (defaultRole == null) {
            throw new RuntimeException("Vai trò mặc định không tồn tại");
        }
        newUser.setRole(defaultRole);

        // Save the user to the repository
        userRepository.save(newUser);
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không thấy người dùng"));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(new Date());
        userRepository.save(user);
    }

    public String getUserRole(User user) {
        return user.getRole().getRoleName();
    }
}

