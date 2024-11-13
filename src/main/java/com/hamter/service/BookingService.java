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
	
	Booking cancelBooking(Long id, String reason);
	
	Booking completeBooking(Long id);
	
	Booking notAttendedBooking(Long id);
	
	void sendReminders();
	
	Booking cancelBookingPending(Long id);
	
	boolean canCreateNewBooking(String patientId);
	
	void checkStatusNotAttendedBooking(Long id, String statusId);
	
	void sendWarningEmail(Booking booking);
}
