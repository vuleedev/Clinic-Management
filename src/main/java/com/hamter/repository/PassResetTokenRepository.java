package com.hamter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamter.model.PassResetToken;

@Repository
public interface PassResetTokenRepository extends JpaRepository<PassResetToken, Long> {

	Optional<PassResetToken> findByToken(String token);
}
