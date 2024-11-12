package com.hamter.service;

import java.util.Date;
import java.util.List;

import com.hamter.model.Booking;
import com.hamter.model.Schedule;

public interface BookingService {
	
	List<Booking> findAll();
	
	Booking findById(Long id);
	
	Booking create(Booking booking);
	
	Booking update(Booking booking);
	
	void delete(Long id);
	
	Booking confirmBooking(Long id);
	
	Booking cancelBooking(Long id, String reason);
	
	void sendReminders();
	
	Booking cancelBookingPending(Long id);
	
	boolean canCreateNewBooking(String patientId);
	
	Booking completeBooking(Long id);
	
	Booking notAttendedBooking(Long id);
	
	void checkStatusNotAttendedBooking(Long id, String status2Id);
	
	void sendWarningEmail(Booking booking);
}
