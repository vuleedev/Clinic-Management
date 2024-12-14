package com.hamter.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hamter.model.Booking;
import com.hamter.model.Doctor;
import com.hamter.model.TimeSlot;
import com.hamter.model.User;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDetailsDTO {

	private Booking booking;
    private Doctor doctor;
    private TimeSlot timeSlot;
    private User user;

    public BookingDetailsDTO(Booking booking, Doctor doctor, TimeSlot timeSlot, User user) {
        this.booking = booking;
        this.doctor = doctor;
        this.timeSlot = timeSlot;
        this.user = user;
    }
}
