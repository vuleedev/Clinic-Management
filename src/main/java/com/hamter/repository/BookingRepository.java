<<<<<<< HEAD

package com.hamter.repository;
=======
package com.hamter.repository;

>>>>>>> 5fb944f1f4a053db8ba3b8c811043d66612133cb
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hamter.model.Bookings;

public interface BookingRepository extends JpaRepository<Bookings, Long> {
	
	@Query("SELECT b FROM Booking b WHERE b.date BETWEEN :start AND :end AND b.statusId = 'CONFIRMED'")
<<<<<<< HEAD
    List<Bookings> findBookingsBetweenDates(Date start, Date end);
=======
    List<Booking> findBookingsBetweenDates(Date start, Date end);
	
    Optional<Booking> findTopByPatientIdOrderByDateDesc(String patientId);
>>>>>>> 5fb944f1f4a053db8ba3b8c811043d66612133cb
}
