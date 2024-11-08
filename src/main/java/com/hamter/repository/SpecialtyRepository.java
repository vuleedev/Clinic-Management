package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Specialties;

public interface SpecialtyRepository extends JpaRepository<Specialties, Long> {

}
