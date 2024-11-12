package com.hamter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long>{
	Optional<Roles> findByName(String name);  // Tìm kiếm vai trò theo tên
}
