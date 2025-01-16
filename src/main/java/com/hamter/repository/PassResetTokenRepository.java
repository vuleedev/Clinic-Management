package com.hamter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamter.entity.PassToken;

@Repository
public interface PassResetTokenRepository extends JpaRepository<PassToken, Long> {

	Optional<PassToken> findByToken(String token);
}
