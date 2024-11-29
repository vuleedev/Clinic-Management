package com.hamter.mapper;

import org.modelmapper.ModelMapper;

import com.hamter.dto.BookingDTO;
import com.hamter.model.Booking;

import com.hamter.repository.DoctorRepository;
import com.hamter.repository.TimeSlotRepository;
import com.hamter.repository.UserRepository;

public class BookingMapper {
	
	private static final ModelMapper modelMapper = new ModelMapper();

    public static BookingDTO toDTO(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    public static Booking toEntity(BookingDTO dto, DoctorRepository doctorRepository, TimeSlotRepository timeSlotRepository, UserRepository userRepository) {
        Booking booking = modelMapper.map(dto, Booking.class);

        booking.setDoctor(doctorRepository.findById(dto.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found")));
        booking.setPatient(userRepository.findById(dto.getPatientId()).orElseThrow(() -> new RuntimeException("User not found")));
        booking.setTimeSlot(timeSlotRepository.findById(dto.getTimeSlotId()).orElseThrow(() -> new RuntimeException("TimeSlot not found")));

        return booking;
    }
}
