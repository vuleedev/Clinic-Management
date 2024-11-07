package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Schedules;

public interface ScheduleRepository extends JpaRepository<Schedules, Long>{
	
}
