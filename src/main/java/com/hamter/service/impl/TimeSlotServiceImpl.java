package com.hamter.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.TimeSlot;
import com.hamter.repository.TimeSlotRepository;
import com.hamter.service.TimeSlotService;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {
	
	@Autowired
	TimeSlotRepository timeSlotRepository;
	
	@Override
	public List<TimeSlot> findAvailableTimeSlots(Long doctorId, Date date) {
		return timeSlotRepository.findAvailableTimeSlots(doctorId, date);
	}

}
