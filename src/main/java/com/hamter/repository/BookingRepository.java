package com.hamter.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hamter.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	@Query("SELECT b FROM Booking b WHERE b.date BETWEEN :start AND :end AND b.statusId = 'CONFIRMED'")
    List<Booking> findBookingsBetweenDates(Date start, Date end);
	
    Optional<Booking> findTopByPatientIdOrderByIdDesc(String patientId);
    
    int countByPatientIdAndStatus2Id(String patientId, String status2Id);
    Optional<Booking> findByIdOrderByDateDesc(Long id);
}
