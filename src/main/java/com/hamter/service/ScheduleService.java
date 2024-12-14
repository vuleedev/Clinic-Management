package com.hamter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.Schedule;
import com.hamter.model.TimeSlot;
import com.hamter.repository.ScheduleRepository;
import com.hamter.repository.TimeSlotRepository;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private TimeSlotRepository timeSlotRepository;

	public List<List<TimeSlot>> getTimeSlotsBySchedule(Long scheduleId) {
        Optional<Schedule> currentScheduleOpt = scheduleRepository.findById(scheduleId);
        if (!currentScheduleOpt.isPresent()) {
            throw new RuntimeException("loi");
        }
        Schedule currentSchedule = currentScheduleOpt.get();
        Optional<Schedule> previousScheduleOpt = scheduleRepository.findTopByDateBeforeOrderByDateDesc(currentSchedule.getDate());
        if (!previousScheduleOpt.isPresent()) {
            throw new RuntimeException("loi");
        }
        Schedule previousSchedule = previousScheduleOpt.get();
        List<TimeSlot> currentTimeSlots = timeSlotRepository.findByScheduleId(currentSchedule.getId());
        List<TimeSlot> previousTimeSlots = timeSlotRepository.findByScheduleId(previousSchedule.getId());

        return List.of(currentTimeSlots, previousTimeSlots);
    }

	public List<Schedule> getScheduleByDoctorId(Long doctorId) {
        return scheduleRepository.findByDoctorId(doctorId);
    }

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
