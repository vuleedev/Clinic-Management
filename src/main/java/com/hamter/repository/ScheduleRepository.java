package com.hamter.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamter.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	Optional<Schedule> findTopByDateBeforeOrderByDateDesc(Date date);

	List<Schedule> findByDoctorId(Long doctorId);
}
