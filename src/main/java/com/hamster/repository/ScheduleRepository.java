package com.hamster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamster.model.Schedules;

public interface ScheduleRepository extends JpaRepository<Schedules, Long>{
	
}
