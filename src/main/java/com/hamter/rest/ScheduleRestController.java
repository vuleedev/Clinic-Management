package com.hamter.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.ScheduleDTO;
import com.hamter.mapper.ScheduleMapper;
import com.hamter.model.Schedule;
import com.hamter.model.TimeSlot;
import com.hamter.repository.DoctorRepository; // Injecting DoctorRepository
import com.hamter.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleRestController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private DoctorRepository doctorRepository; 

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAll().stream()
            .map(ScheduleMapper::toDTO)
            .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @GetMapping("/schedule/{doctorId}")
    public List<ScheduleDTO> getScheduleByDoctorId(@PathVariable Long doctorId) {
        return scheduleService.getScheduleByDoctorId(doctorId).stream()
            .map(ScheduleMapper::toDTO)
            .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @GetMapping("/timeSlots/{scheduleId}")
    public List<List<TimeSlot>> getTimeSlotsBySchedule(@PathVariable Long scheduleId) {
        return scheduleService.getTimeSlotsBySchedule(scheduleId);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @GetMapping("/{id}")
    public ScheduleDTO getScheduleById(@PathVariable("id") Long id) {
        return ScheduleMapper.toDTO(scheduleService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @PostMapping("create-schedule")
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = ScheduleMapper.toEntity(scheduleDTO, doctorRepository);
        return ScheduleMapper.toDTO(scheduleService.create(schedule));
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @PutMapping("/{id}")
    public ScheduleDTO updateSchedule(@PathVariable("id") Long id, @RequestBody ScheduleDTO scheduleDTO) {
        scheduleDTO.setId(id);
        Schedule schedule = ScheduleMapper.toEntity(scheduleDTO, doctorRepository);
        return ScheduleMapper.toDTO(scheduleService.update(schedule));
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable("id") Long id) {
        scheduleService.delete(id);
    }
}
