
package com.hamter.repository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hamter.model.Bookings;

public interface BookingRepository extends JpaRepository<Bookings, Long> {
	
	//List<Booking> findByStatusId(String statusId);
	
	@Query("SELECT b FROM Booking b WHERE b.date BETWEEN :start AND :end AND b.statusId = 'CONFIRMED'")
    List<Bookings> findBookingsBetweenDates(Date start, Date end);
}
