package com.hamter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hamter.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT DISTINCT ar.user FROM Authority ar WHERE ar.role.id IN('DIRECTOR','STAF')")
	List<User> getAdministrators();
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
}
