package com.hamter.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.hamter.model.Bookings;

public interface BookingRepository extends JpaRepository<Bookings, Long> {
	
	@Query("SELECT b FROM Booking b WHERE b.date BETWEEN :start AND :end AND b.statusId = 'WAIT'")
    List<Bookings> findBookingsBetweenDates(Date start, Date end);
	
    Optional<Bookings> findTopByPatientIdOrderByIdDesc(String patientId);
    
    int countByPatientIdAndStatusId(String patientId, String statusId);
}
