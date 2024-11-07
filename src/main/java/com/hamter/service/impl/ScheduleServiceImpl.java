package com.hamter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.ScheduleRepository;
import com.hamter.model.Schedule;
import com.hamter.service.ScheduleService;
import java.util.Date;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Override
	public List<Schedule> findAll() {
		return scheduleRepository.findAll();
	}

	@Override
	public Schedule findById(Long id) {
		return scheduleRepository.findById(id).orElse(null);
	}

	@Override
	public Schedule create(Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

	@Override
	public Schedule update(Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

	@Override
	public void delete(Long id) {
		scheduleRepository.deleteById(id);
	}

	@Override
	public boolean isTimeSlotAvailable(Integer doctorId, Date date, String timeType) {
		List<Schedule> schedules = scheduleRepository.findByDoctorIdAndDateAndTimeType(doctorId, date, timeType);
	    return schedules.isEmpty();
	}

}
