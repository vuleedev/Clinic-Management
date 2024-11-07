package com.hamter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hamter.model.Authority;
import com.hamter.model.User;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	
	@Query("SELECT DISTINCT a FROM Authority a WHERE a.user IN ?1")
	List<Authority> authoritiesOf(List<User> user);
}
