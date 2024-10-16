package com.hamter.service;

import java.util.List;

import com.hamter.model.Booking;

public interface BookingService {
	
	List<Booking> findAll();
	
	Booking findById(Long id);
	
	Booking create(Booking booking);
	
	Booking update(Booking bookingDetails);
	
	void delete(Long id);
}
