package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamter.model.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
	
}
