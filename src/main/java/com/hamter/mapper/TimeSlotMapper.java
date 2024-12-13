package com.hamter.mapper;

import java.time.LocalTime;

import org.modelmapper.ModelMapper;

import com.hamter.dto.TimeSlotDTO;
import com.hamter.model.TimeSlot;
import com.hamter.repository.DoctorRepository;
import com.hamter.repository.ScheduleRepository;

public class TimeSlotMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static TimeSlotDTO toDTO(TimeSlot timeSlot) {
        TimeSlotDTO dto = modelMapper.map(timeSlot, TimeSlotDTO.class);

        dto.setStartTime(timeSlot.getStartTime() != null ? timeSlot.getStartTime().toString() : null);
        dto.setEndTime(timeSlot.getEndTime() != null ? timeSlot.getEndTime().toString() : null);

        return dto;
    }

    public static TimeSlot toEntity(TimeSlotDTO dto, DoctorRepository doctorRepository, ScheduleRepository scheduleRepository) {
        TimeSlot timeSlot = modelMapper.map(dto, TimeSlot.class);

        if (dto.getStartTime() != null) {
            timeSlot.setStartTime(LocalTime.parse(dto.getStartTime())); // Chuyển từ String sang LocalTime
        }
        if (dto.getEndTime() != null) {
            timeSlot.setEndTime(LocalTime.parse(dto.getEndTime())); // Chuyển từ String sang LocalTime
        }

        timeSlot.setDoctor(doctorRepository.findById(dto.getDoctorId())
            .orElseThrow(() -> new RuntimeException("không tìm thấy doctor")));
        timeSlot.setSchedule(scheduleRepository.findById(dto.getScheduleId())
            .orElseThrow(() -> new RuntimeException("không tìm thấy schedule")));

        return timeSlot;
    }


}
