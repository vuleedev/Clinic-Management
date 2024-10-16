package com.hamter.authService;

import com.hamter.model.User;

public interface AuthService {
	
	void updateToken(String token, String email) throws Exception;

	User getByToken(String token);

	void updatePassword(User entity, String newPassword);

	void changePassword(User entity, String newPassword);
}
