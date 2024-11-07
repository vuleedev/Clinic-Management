package com.hamter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.Authority;
import com.hamter.model.User;
import com.hamter.repository.AuthorityRepository;
import com.hamter.repository.UserRepository;
import com.hamter.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public List<Authority> findAuthoritiesOfAdmin() {
		List<User> user = userRepository.getAdministrators();
		return authorityRepository.authoritiesOf(user);
	}

	@Override
	public List<Authority> findAll() {
		return authorityRepository.findAll();
	}

	@Override
	public Authority create(Authority auth) {
		return authorityRepository.save(auth);
	}

	@Override
	public void delete(Long id) {
		authorityRepository.deleteById(id);
	}
	
}
