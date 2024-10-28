package com.hamter.service;

import java.util.Date;
import java.util.List;

import com.hamter.model.Schedule;

public interface ScheduleService {
	
	List<Schedule> findAll();
	
	Schedule findById(Long id);
	
	Schedule create (Schedule schedule);
	
	Schedule update (Schedule schedule);
	
	void delete (Long id);
	
	boolean isTimeSlotAvailable(Integer doctorId, Date date, String timeType);
}
