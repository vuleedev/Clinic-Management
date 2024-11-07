package com.hamter.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hamter.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	//List<Booking> findByStatusId(String statusId);
	
	@Query("SELECT b FROM Booking b WHERE b.date BETWEEN :start AND :end AND b.statusId = 'CONFIRMED'")
    List<Booking> findBookingsBetweenDates(Date start, Date end);
}
