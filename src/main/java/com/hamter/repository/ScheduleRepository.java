package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
	
}
