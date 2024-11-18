package com.hamter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.Authority;
import com.hamter.model.User;
import com.hamter.repository.AuthorityRepository;
import com.hamter.repository.UserRepository;
import com.hamter.service.AuthorityService;

@Service
public class AuthorityService {

	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	UserRepository userRepository;

	public List<Authority> findAuthoritiesOfAdmin() {
		List<User> user = userRepository.getAdministrators();
		return authorityRepository.authoritiesOf(user);
	}

	public List<Authority> findAll() {
		return authorityRepository.findAll();
	}

	public Authority create(Authority auth) {
		return authorityRepository.save(auth);
	}

	public void delete(Long id) {
		authorityRepository.deleteById(id);
	}
	
}
