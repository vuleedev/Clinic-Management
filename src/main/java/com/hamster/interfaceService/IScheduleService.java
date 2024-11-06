package com.hamster.interfaceService;

import java.util.List;
import java.util.Optional;

import com.hamter.dto.ScheduleDTO;
import com.hamter.model.Schedule;

public interface IScheduleService {
	List<ScheduleDTO> getAllSchedules();
	
	ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) throws Exception;
	
	ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO);
	
	Optional<ScheduleDTO> getScheduleById(Long id);
	
	List<ScheduleDTO> getAllScheduleById(Long id);
	
	void deleteSchedule(Long id);
	
}
