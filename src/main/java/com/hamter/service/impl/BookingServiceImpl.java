package com.hamter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.BookingRepository;
import com.hamter.model.Booking;
import com.hamter.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Override
	public List<Booking> findAll() {
		return bookingRepository.findAll();
	}

	@Override
	public Booking findById(Long id) {
		return bookingRepository.findById(id).orElse(null);
	}

	@Override
	public Booking create(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public Booking update(Booking bookingDetails) {
		if (bookingRepository.existsById(bookingDetails.getId())) {
            return bookingRepository.save(bookingDetails);
        }
        return new Booking();
	}

	@Override
	public void delete(Long id) {
		bookingRepository.deleteById(id);
		
	}

}
