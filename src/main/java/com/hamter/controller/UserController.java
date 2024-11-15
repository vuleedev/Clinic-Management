package com.hamter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.UserDTO;
import com.hamter.dto.UserLoginDTO;
import com.hamter.model.Roles;
import com.hamter.model.User;
import com.hamter.repository.RoleRepository;
import com.hamter.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
    private RoleRepository roleRepository;
	// đăng ký
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult rs) {
		try {
			if(rs.hasErrors()) {
				List<String> errorMessages = rs.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
				return ResponseEntity.badRequest().body(errorMessages);
			}
			if(!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
				return ResponseEntity.badRequest().body("Passwords do not match");
			}
			Optional<Roles> roleOptional = roleRepository.findByName(userDTO.getRoleName());
            if (roleOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid role");
            }
			User user = userService.register(userDTO, roleOptional.get());
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// đăng nhập
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
		// Kiểm tra thông tin đăng nhập và tạo ra token
		try {
			String token = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// đăng xuất
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization")String token){
		String jwtToken = token.replace("Bearer ", "");
		userService.logout(jwtToken);
		return ResponseEntity.ok("Loggout successfully");
	}
}
