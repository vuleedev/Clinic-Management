package com.hamter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	List<Doctor> findBySpecialtyId(Long specialtyId);
}
