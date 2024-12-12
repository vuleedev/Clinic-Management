package com.hamter.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hamter.model.TimeSlot;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
      
	@Query("SELECT ts FROM TimeSlot ts " +
		       "JOIN ts.schedule s " +
		       "WHERE s.doctor.id = :doctorId " +
		       "AND s.date = :date " +
		       "AND ts.isAvailable = true")
		List<TimeSlot> findAvailableTimeSlots(@Param("doctorId") Long doctorId, 
		                                      @Param("date") Date date);

	

}
