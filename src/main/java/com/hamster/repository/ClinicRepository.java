package com.hamster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamster.model.Clinics;

public interface ClinicRepository extends JpaRepository<Clinics, Long> {

}
