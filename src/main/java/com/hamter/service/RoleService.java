package com.hamter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.Role;
import com.hamter.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role findByRoleId(Long id) {
		return roleRepository.findById(id).orElse(null);
	}
	public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElse(null);
    }
}
