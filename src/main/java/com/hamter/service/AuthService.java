package com.hamter.service;

import org.springframework.stereotype.Service;

import com.hamter.model.Authority;
import com.hamter.model.Role;
import com.hamter.model.User;
import com.hamter.repository.AuthorityRepository;
import com.hamter.repository.UserRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String email, String password, String firstName, String lastName, Role role) {
        
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);  
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());

       
        userRepository.save(newUser);

        
        Authority authority = new Authority();
        authority.setUser(newUser);
        authority.setRole(role);

        
        authorityRepository.save(authority);
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("không thấy người dùng"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(new Date());
        userRepository.save(user);
    }
}
