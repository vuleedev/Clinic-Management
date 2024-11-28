package com.hamter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.auth.CPasswordRequest;
import com.hamter.dto.auth.LoginRequest;
import com.hamter.dto.auth.RegisterRequest;
import com.hamter.model.Role;
import com.hamter.model.User;
import com.hamter.service.AuthService;
import com.hamter.service.RoleService;
import com.hamter.util.JwTokenUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
	
	private final AuthService authService;
    private final JwTokenUtil jwtUtil;

    @Autowired
    public AuthRestController(AuthService authService, JwTokenUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.registerUser(registerRequest.getEmail(), registerRequest.getPassword());
            return ResponseEntity.ok("Đăng ký tài khoản thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình đăng ký: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Perform login
            User user = authService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            String role = authService.getUserRole(user);

            // Generate JWT token with user ID and role
            String token = jwtUtil.generateToken(user.getId(), List.of(role));
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Đăng nhập không thành công: " + e.getMessage());
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody CPasswordRequest changePasswordRequest) {
        try {
            authService.changePassword(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
            return ResponseEntity.ok("Đổi mật khẩu thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình thay đổi mật khẩu: " + e.getMessage());
        }
    }
}
