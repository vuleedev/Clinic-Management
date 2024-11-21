package com.hamter.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.auth.CPasswordRequest;
import com.hamter.dto.auth.LoginRequest;
import com.hamter.dto.auth.RegisterRequest;
import com.hamter.service.AuthService;
import com.hamter.util.JwTokenUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

	private final AuthService authService;
    private final JwTokenUtil jwtUtil;

    public AuthRestController(AuthService authService, JwTokenUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.registerUser(
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName()
            );
            return ResponseEntity.ok("Đăng ký tài khoản thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình đăng ký: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Đăng nhập không thành công: " + e.getMessage());
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody CPasswordRequest changePasswordRequest) {
        try {
            authService.changePassword(
                changePasswordRequest.getEmail(),
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword()
            );
            return ResponseEntity.ok("Đổi mật khẩu thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi trong quá trình thay đổi mật khẩu: " + e.getMessage());
        }
    }
}
