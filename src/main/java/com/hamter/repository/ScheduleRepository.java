package com.hamter.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
	//boolean existsByDoctorIdAndDateAndTimeType(Integer doctorId, Date date, String timeType);
	
	List<Schedule> findByDoctorIdAndDateAndTimeType(Integer doctorId, Date date, String timeType);
}
