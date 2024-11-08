package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.DoctorInfo;

public interface DoctorInfoRepository extends JpaRepository<DoctorInfo, Long> {

}
