package com.hamster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamster.model.TimeSlot;
import com.hamster.repository.TimeSlotRepository;

@Service
public class TimeSlotService {
	
	@Autowired
	TimeSlotRepository timeSlotRepository;
	
	public List<TimeSlot> findAll() {
		return timeSlotRepository.findAll();
	}

	public TimeSlot findById(Long id) {
		return timeSlotRepository.findById(id).orElse(null);
	}

	public TimeSlot create(TimeSlot timeSlot) {
		return timeSlotRepository.save(timeSlot);
	}

	public TimeSlot update(TimeSlot timeSlot) {
		return timeSlotRepository.save(timeSlot);
	}

	public void delete(Long id) {
		timeSlotRepository.deleteById(id);
		
	}
	
	public List<TimeSlot> findAvailableTimeSlots(Integer doctorId, Date date) {
		return timeSlotRepository.findAvailableTimeSlots(doctorId, date);
	}
}