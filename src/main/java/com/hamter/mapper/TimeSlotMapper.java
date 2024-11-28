package com.hamter.mapper;

import com.hamter.dto.TimeSlotDTO;
import com.hamter.model.TimeSlot;

public class TimeSlotMapper {

	public static TimeSlotDTO toDTO(TimeSlot timeSlot) {
        TimeSlotDTO dto = new TimeSlotDTO();
        dto.setId(timeSlot.getId());
        dto.setDoctorId(timeSlot.getDoctor().getId()); 
        dto.setScheduleId(timeSlot.getSchedule().getId()); 
        dto.setStartTime(timeSlot.getStartTime());
        dto.setEndTime(timeSlot.getEndTime());
        dto.setIsAvailable(timeSlot.getIsAvailable());
        dto.setCreatedAt(timeSlot.getCreatedAt());
        dto.setUpdatedAt(timeSlot.getUpdatedAt());
        return dto;
    }

    public static TimeSlot toEntity(TimeSlotDTO dto) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(dto.getId());
        timeSlot.setStartTime(dto.getStartTime());
        timeSlot.setEndTime(dto.getEndTime());
        timeSlot.setIsAvailable(dto.getIsAvailable());
        return timeSlot;
    }
}
