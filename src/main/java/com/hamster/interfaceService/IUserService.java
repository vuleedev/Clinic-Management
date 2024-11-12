package com.hamster.interfaceService;

import com.hamter.dto.UserDTO;
import com.hamter.model.Roles;
import com.hamter.model.User;

public interface IUserService {
	User register(UserDTO userDTO, Roles role) throws Exception;
	
	String login(String email, String password) throws Exception;
	
	String logout(String token);
}
