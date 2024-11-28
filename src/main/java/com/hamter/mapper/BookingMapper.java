package com.hamter.mapper;

import com.hamter.dto.BookingDTO;
import com.hamter.model.Booking;

import com.hamter.repository.DoctorRepository;
import com.hamter.repository.TimeSlotRepository;
import com.hamter.repository.UserRepository;

public class BookingMapper {
	
    public static BookingDTO toDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setStatusId(booking.getStatusId());
        dto.setDoctorId(booking.getDoctor().getId()); 
        dto.setPatientId(booking.getPatient().getId());
        dto.setEmail(booking.getEmail());
        dto.setCancelReason(booking.getCancelReason());
        dto.setDate(booking.getDate());
        dto.setTimeType(booking.getTimeType());
        dto.setTimeSlotId(booking.getTimeSlot().getId()); 
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        return dto;
    }

    public static Booking toEntity(BookingDTO dto, DoctorRepository doctorRepository, TimeSlotRepository timeSlotRepository, UserRepository userRepository) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setStatusId(dto.getStatusId());
        booking.setDoctor(doctorRepository.findById(dto.getDoctorId()).get());
        booking.setPatient(userRepository.findById(dto.getPatientId()).get());
        booking.setEmail(dto.getEmail());
        booking.setCancelReason(dto.getCancelReason());
        booking.setDate(dto.getDate());
        booking.setTimeType(dto.getTimeType());
        booking.setTimeSlot(timeSlotRepository.findById(dto.getTimeSlotId()).get());
        return booking;
    }
}
