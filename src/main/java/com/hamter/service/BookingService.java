<<<<<<< HEAD
package com.hamter.service;

public class BookingService {

}
=======
package com.hamter.service;

import java.util.List;

import com.hamter.model.Booking;

public interface BookingService {
	
	List<Booking> findAll();
	
	Booking findById(Long id);
	
	Booking create(Booking booking);
	
	Booking update(Booking booking);
	
	void delete(Long id);
	
	Booking confirmBooking(Long id);
	
	Booking cancelBooking(Long id);
	
	void sendReminders();
	
	Booking cancelBookingPending(Long id);

}
>>>>>>> 545840a2952baa7f9c05c319d3d612e18e74e73a
