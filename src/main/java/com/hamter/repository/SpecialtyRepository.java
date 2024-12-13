package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamter.model.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

}
