package com.hamter.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.TimeSlot;
import com.hamter.repository.DoctorRepository;
import com.hamter.repository.ScheduleRepository;
import com.hamter.repository.TimeSlotRepository;

@Service
public class TimeSlotService {

	@Autowired
	TimeSlotRepository timeSlotRepository;

	@Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

	public List<TimeSlot> findAvailableTimeSlots(Long doctorId, Date date) {
		return timeSlotRepository.findAvailableTimeSlots(doctorId, date);
	}

	public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    public TimeSlot getTimeSlotById(Long id) {
        return timeSlotRepository.findById(id).orElse(null);
    }

    public TimeSlot saveOrUpdateTimeSlot(TimeSlot timeSlot) {
        return timeSlotRepository.save(timeSlot);
    }

    public void deleteTimeSlot(Long id) {
        timeSlotRepository.deleteById(id);
    }

    public DoctorRepository getDoctorRepository() {
        return doctorRepository;
    }

    public ScheduleRepository getScheduleRepository() {
        return scheduleRepository;
    }
}
