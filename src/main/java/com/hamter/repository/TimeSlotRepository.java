package com.hamter.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hamter.model.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

	@Query("SELECT ts FROM TimeSlot ts " +
	           "JOIN ts.schedule s " +
	           "WHERE s.doctor.id = :doctorId " +
	           "AND s.date = :date " +
	           "AND ts.isAvailable = true")
	    List<TimeSlot> findAvailableTimeSlots(@Param("doctorId") Long doctorId, 
	                                          @Param("date") Date date);
	
//	@Query("SELECT ts FROM TimeSlot ts " +
//	           "JOIN ts.schedule s " +
//	           "JOIN s.doctor d " +
//	           "JOIN d.specialty sp " +  // Liên kết với Specialty
//	           "WHERE sp.id = :specialtyId " +  // Lọc theo chuyên khoa
//	           "AND s.date = :date " +
//	           "AND ts.isAvailable = true")
//	List<TimeSlot> findAvailableTimeSlots(@Param("specialtyId") Long specialtyId, 
//	                                      @Param("date") Date date);
	
}
