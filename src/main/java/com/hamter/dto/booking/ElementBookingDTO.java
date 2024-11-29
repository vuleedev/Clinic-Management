package com.hamter.dto.booking;

import java.util.List;

import com.hamter.dto.DoctorDTO;
import com.hamter.dto.TimeSlotDTO;

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
