package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
