package com.hamter.service;

import java.util.Date;
import java.util.List;

import com.hamter.model.TimeSlot;

public interface TimeSlotService {
	
	List<TimeSlot> findAvailableTimeSlots(Long doctorId, Date date);
}
