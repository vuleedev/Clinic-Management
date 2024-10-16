package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

}
