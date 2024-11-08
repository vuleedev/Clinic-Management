package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Clinics;

public interface ClinicRepository extends JpaRepository<Clinics, Long> {

}
