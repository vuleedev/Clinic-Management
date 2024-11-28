package com.hamter.dto;

import java.util.List;

import lombok.Data;

@Data
public class ElementBookingDTO {

	private DoctorDTO doctor;
    private List<TimeSlotDTO> availableTimeSlots;

    public ElementBookingDTO(DoctorDTO doctor, List<TimeSlotDTO> availableTimeSlots) {
        this.doctor = doctor;
        this.availableTimeSlots = availableTimeSlots;
    }
}
