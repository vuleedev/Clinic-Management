package com.hamter.authService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;


import com.hamter.model.User;
import com.hamter.authService.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Override
	public void updateToken(String token, String email) throws Exception {
		
	}

	@Override
	public User getByToken(String token) {
		return null;
	}

	@Override
	public void updatePassword(User entity, String newPassword) {
		
	}

	@Override
	public void changePassword(User entity, String newPassword) {
		
	}
}
