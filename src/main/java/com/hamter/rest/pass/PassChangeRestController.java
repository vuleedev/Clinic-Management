package com.hamter.rest.pass;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.auth.PassResetRequest;
import com.hamter.dto.auth.ResponseMessage;
import com.hamter.model.PassResetToken;
import com.hamter.repository.PassResetTokenRepository;
import com.hamter.service.ResetPassService;

@RestController
public class PassChangeRestController {

    @Autowired
    private PassResetTokenRepository passResetTokenRepository;

    @Autowired
    private ResetPassService resetPassService;

    @GetMapping("/reset-password")
    public ResponseEntity<?> checkToken(@RequestParam String token) {
        PassResetToken resetToken = passResetTokenRepository.findByToken(token).orElse(null);
        if (resetToken == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Token không hợp lệ", "error", null));
        }
        if (resetToken.getExpiryDate().before(new Date())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Token đã hết hạn", "error", null));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "http://localhost:4200/reset-password?token=" + token);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping("/p-reset-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody PassResetRequest request) {
        String message = resetPassService.changeResetPassword(request.getToken(), request.getNewPassword());
        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        if (message.equals("Mật khẩu đã được thay đổi thành công")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
