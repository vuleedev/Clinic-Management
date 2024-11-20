package com.hamter.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String register(@RequestParam String email, @RequestParam String password,
                           @RequestParam String firstName, @RequestParam String lastName) {
        authService.registerUser(email, password, firstName, lastName);
        return "đăng ký tài khoản thành công!";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        String token = jwtUtil.generateToken(email);
        return token;
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String email, @RequestParam String oldPassword,
                                 @RequestParam String newPassword) {
        authService.changePassword(email, oldPassword, newPassword);
        return "đổi mật khẩu thành công!";
    }
}
