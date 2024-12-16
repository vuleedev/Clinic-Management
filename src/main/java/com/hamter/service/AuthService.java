package com.hamter.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hamter.model.Role;
import com.hamter.model.User;
import com.hamter.repository.UserRepository;

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
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email không tồn tại"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }
        return user;
    }

    public void registerUser(String email, String password, String userName, Boolean gender, String andress) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(encodedPassword);
        newUser.setUserName(userName);
        newUser.setGender(gender);
        newUser.setAddress(andress);
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());
        Role defaultRole = roleService.findByRoleName("CUST");
        newUser.setRole(defaultRole);
        userRepository.save(newUser);
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email).orElse(null);
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

