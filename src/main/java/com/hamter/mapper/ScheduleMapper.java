package com.hamter.mapper;

import com.hamter.dto.ScheduleDTO;
import com.hamter.model.Schedule;

public class ScheduleMapper {

	public static ScheduleDTO toDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setCurrentNumber(schedule.getCurrentNumber());
        dto.setMaxNumber(schedule.getMaxNumber());
        dto.setDate(schedule.getDate());
        dto.setTimeType(schedule.getTimeType());
        dto.setDoctorId(schedule.getDoctor().getId()); 
        dto.setCreatedAt(schedule.getCreatedAt());
        dto.setUpdatedAt(schedule.getUpdatedAt());
        return dto;
    }

    public static Schedule toEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setId(dto.getId());
        schedule.setCurrentNumber(dto.getCurrentNumber());
        schedule.setMaxNumber(dto.getMaxNumber());
        schedule.setDate(dto.getDate());
        schedule.setTimeType(dto.getTimeType());
        return schedule;
    }
}
