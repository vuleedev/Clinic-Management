package com.hamter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.hamster.interfaceService.IScheduleService;
import com.hamter.dto.ScheduleDTO;
import com.hamter.model.Schedules;
import com.hamter.repository.ScheduleRepository;

public class ScheduleService implements IScheduleService {
	
	private final ScheduleRepository scheduleRepository;
	
	@Autowired
	public ScheduleService(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	@Override
	public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) throws Exception {
		Schedules schedule = new Schedules();
		schedule.setCurrentNumber(scheduleDTO.getCurrentNumber());
		schedule.setMaxNumber(scheduleDTO.getMaxNumber());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setTimeType(scheduleDTO.getTimeType());
        schedule.setDoctorId(scheduleDTO.getDoctorId());
        schedule = scheduleRepository.save(schedule);
        scheduleDTO.setId(schedule.getId());
		return scheduleDTO;
	}

	@Override
	public ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO) {
		Optional<Schedules> scheduleOpt = scheduleRepository.findById(id);
        if (scheduleOpt.isPresent()) {
            Schedules schedule = scheduleOpt.get();

            schedule.setCurrentNumber(scheduleDTO.getCurrentNumber());
            schedule.setMaxNumber(scheduleDTO.getMaxNumber());
            schedule.setDate(scheduleDTO.getDate());
            schedule.setTimeType(scheduleDTO.getTimeType());
            schedule.setDoctorId(scheduleDTO.getDoctorId());

            schedule = scheduleRepository.save(schedule);

            scheduleDTO.setId(schedule.getId());
            return scheduleDTO;
        }
        return null;
	}

	@Override
	public Optional<ScheduleDTO> getScheduleById(Long id) {
		Optional<Schedules> schedule = scheduleRepository.findById(id);
        if (schedule.isPresent()) {
            ScheduleDTO scheduleDTO = new ScheduleDTO(
                    schedule.get().getId(),
                    schedule.get().getCurrentNumber(),
                    schedule.get().getMaxNumber(),
                    schedule.get().getDate(),
                    schedule.get().getTimeType(),
                    schedule.get().getDoctorId()
            );
            return Optional.of(scheduleDTO);
        }
        return Optional.empty();
	}

	@Override
	public List<ScheduleDTO> getAllScheduleById(Long id) {
		List<Schedules> schedules = scheduleRepository.findAll();
        return schedules.stream().map(schedule -> new ScheduleDTO(
                schedule.getId(),
                schedule.getCurrentNumber(),
                schedule.getMaxNumber(),
                schedule.getDate(),
                schedule.getTimeType(),
                schedule.getDoctorId()
        )).toList();
	}

	@Override
	public void deleteSchedule(Long id) {
		scheduleRepository.deleteById(id);
	}

	@Override
	public List<ScheduleDTO> getAllSchedules() {
List<Schedules> schedules = scheduleRepository.findAll();
        
        // Chuyển các đối tượng Schedule thành ScheduleDTO
        return schedules.stream().map(schedule -> new ScheduleDTO(
                schedule.getId(),
                schedule.getCurrentNumber(),
                schedule.getMaxNumber(),
                schedule.getDate(),
                schedule.getTimeType(),
                schedule.getDoctorId()
        )).collect(Collectors.toList());
	}
	

}
