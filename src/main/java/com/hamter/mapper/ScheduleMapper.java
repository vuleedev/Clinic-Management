package com.hamter.mapper;

import org.modelmapper.ModelMapper;

import com.hamter.dto.ScheduleDTO;
import com.hamter.model.Schedule;
import com.hamter.repository.DoctorRepository;

public class ScheduleMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ScheduleDTO toDTO(Schedule schedule) {
        return modelMapper.map(schedule, ScheduleDTO.class);
    }

    public static Schedule toEntity(ScheduleDTO dto, DoctorRepository doctorRepository) {
        Schedule schedule = modelMapper.map(dto, Schedule.class);

        schedule.setDoctor(doctorRepository.findById(dto.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found")));

        return schedule;
    }
}
