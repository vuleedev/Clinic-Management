package com.hamter.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.auth.CPasswordRequest;
import com.hamter.dto.auth.LoginRequest;
import com.hamter.dto.auth.RegisterRequest;
import com.hamter.model.User;
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
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.registerUser(registerRequest.getEmail(),
            						 registerRequest.getPassword(),
            						 registerRequest.getUserName(),
            						 registerRequest.getGender(),
            						 registerRequest.getAddress());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Đăng ký tài khoản thành công!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Lỗi trong quá trình đăng ký: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = authService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            String role = authService.getUserRole(user);
            String token = jwtUtil.generateToken(user.getId(), List.of(role));
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Đăng nhập không thành công: " + e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasAnyAuthority('CUST', 'STAFF', 'MANAGE')")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody CPasswordRequest changePasswordRequest) {
    	try {
            authService.changePassword(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Đổi mật khẩu thành công!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Lỗi trong quá trình thay đổi mật khẩu: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

}
