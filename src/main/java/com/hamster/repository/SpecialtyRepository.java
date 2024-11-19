package com.hamster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamster.model.Specialties;

public interface SpecialtyRepository extends JpaRepository<Specialties, Long> {

}
