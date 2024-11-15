package com.hamter.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hamter.model.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
	@Query("SELECT ts FROM TimeSlot ts JOIN ts.schedule s " +
		       "WHERE s.doctorId = :doctorId AND s.date = :date " +
		       "AND ts.isAvailable = true " +
		       "AND ts.id NOT IN (SELECT b.timeSlot.id FROM Booking b WHERE b.date = :date AND b.doctorId = :doctorId)")
		List<TimeSlot> findAvailableTimeSlots(@Param("doctorId") Integer doctorId, @Param("date") Date date);
}
