package com.hamter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.ScheduleRepository;
import com.hamter.model.Schedule;
import com.hamter.service.ScheduleService;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	public List<Schedule> findAll() {
		return scheduleRepository.findAll();
	}

	public Schedule findById(Long id) {
		return scheduleRepository.findById(id).orElse(null);
	}

	public Schedule create(Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

	public Schedule update(Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

	public void delete(Long id) {
		scheduleRepository.deleteById(id);
	}

}
