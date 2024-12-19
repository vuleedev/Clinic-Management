package com.hamter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamter.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
	
	
	List<History> findByDoctorId(Long doctorId);
	
	History findByUser_Id(Long userId);
	
	List<History> findAllByUser_Id(Long userId);
}
