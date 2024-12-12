package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamter.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
		
}
