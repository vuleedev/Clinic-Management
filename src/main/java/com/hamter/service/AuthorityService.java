package com.hamter.service;

import java.util.List;

import com.hamter.model.Authority;


public interface AuthorityService {
	
	List<Authority> findAuthoritiesOfAdmin();

	List<Authority> findAll();

	Authority create(Authority auth);

	void delete(Long id);
}
