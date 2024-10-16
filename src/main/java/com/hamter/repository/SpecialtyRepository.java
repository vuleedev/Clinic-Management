package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

}
