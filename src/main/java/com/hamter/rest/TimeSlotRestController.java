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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.TimeSlotDTO;
import com.hamter.mapper.TimeSlotMapper;
import com.hamter.service.TimeSlotService;

@RestController
@RequestMapping("/api/time-slots")
public class TimeSlotRestController {

    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    public List<TimeSlotDTO> getAllTimeSlots() {
        return timeSlotService.getAllTimeSlots().stream()
            .map(TimeSlotMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @GetMapping("/doctor/{doctorId}/timeslot")
    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    public List<TimeSlotDTO> getTimeSlotByDoctor(@PathVariable Long doctorId) {
        return timeSlotService.findTimeSlotByDoctor(doctorId).stream()
            .map(TimeSlotMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @GetMapping("/{id}")
    public TimeSlotDTO getTimeSlotById(@PathVariable("id") Long id) {
        return TimeSlotMapper.toDTO(timeSlotService.getTimeSlotById(id));
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @PostMapping("/create-timeslot")
    public TimeSlotDTO createTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) {
        return TimeSlotMapper.toDTO(timeSlotService.saveOrUpdateTimeSlot(
            TimeSlotMapper.toEntity(timeSlotDTO, timeSlotService.getDoctorRepository(), timeSlotService.getScheduleRepository())));
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @PutMapping("/{id}")
    public TimeSlotDTO updateTimeSlot(@PathVariable("id") Long id, @RequestBody TimeSlotDTO timeSlotDTO) {
        timeSlotDTO.setId(id);
        return TimeSlotMapper.toDTO(timeSlotService.saveOrUpdateTimeSlot(
            TimeSlotMapper.toEntity(timeSlotDTO, timeSlotService.getDoctorRepository(), timeSlotService.getScheduleRepository())));
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @DeleteMapping("/{id}")
    public void deleteTimeSlot(@PathVariable("id") Long id) {
        timeSlotService.deleteTimeSlot(id);
    }
}
