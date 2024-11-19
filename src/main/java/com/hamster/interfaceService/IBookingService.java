
package com.hamster.interfaceService;

import java.util.List;

import com.hamster.model.Bookings;

public interface IBookingService {
	
	List<Bookings> findAll();
	
	Bookings findById(Long id);
	
	Bookings create(Bookings booking);
	
	Bookings update(Bookings booking);
	
	void delete(Long id);
	
	Bookings confirmBooking(Long id);
	
	Bookings cancelBooking(Long id);
	
	void sendReminders();
	
	Bookings cancelBookingPending(Long id);

}

