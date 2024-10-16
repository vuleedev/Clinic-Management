package com.hamter.service;

import java.util.List;

import com.hamter.model.User;

public interface UserService {
	
	List<User> findAll();
	
	User findById(Long id);
	
	User create (User user);
	
	User update (User user);
	
	void delete (Long id);
}
