package com.hamster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamster.model.DoctorInfo;

public interface DoctorInfoRepository extends JpaRepository<DoctorInfo, Long> {

}
