package com.hamster.interfaceService;

import com.hamster.dto.UserDTO;
import com.hamster.model.Roles;
import com.hamster.model.User;

public interface IUserService {
	User register(UserDTO userDTO) throws Exception;
	
	String login(String email, String password) throws Exception;
	
	String logout(String token);
}
