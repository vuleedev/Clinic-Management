package com.hamter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hamter.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	@Query("SELECT d FROM Doctor d WHERE d.specialty.id = :specialtyId")
    List<Doctor> findDoctorsBySpecialty(@Param("specialtyId") Long specialtyId);
}
