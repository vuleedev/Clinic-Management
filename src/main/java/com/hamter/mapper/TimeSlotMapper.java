package com.hamter.mapper;

import org.modelmapper.ModelMapper;

import com.hamter.dto.TimeSlotDTO;
import com.hamter.model.TimeSlot;
import com.hamter.repository.DoctorRepository;
import com.hamter.repository.ScheduleRepository;

public class TimeSlotMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static TimeSlotDTO toDTO(TimeSlot timeSlot) {
        return modelMapper.map(timeSlot, TimeSlotDTO.class);
    }

    public static TimeSlot toEntity(TimeSlotDTO dto, DoctorRepository doctorRepository, ScheduleRepository scheduleRepository) {
        TimeSlot timeSlot = modelMapper.map(dto, TimeSlot.class);

        timeSlot.setDoctor(doctorRepository.findById(dto.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found")));
        timeSlot.setSchedule(scheduleRepository.findById(dto.getScheduleId()).orElseThrow(() -> new RuntimeException("Schedule not found")));

        return timeSlot;
    }
}
