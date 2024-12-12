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

        booking.setDoctor(doctorRepository.findById(dto.getDoctorId()).orElseThrow(() -> new RuntimeException("không tìm thấy doctor")));
        booking.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("không tìm thấy user")));
        booking.setTimeSlot(timeSlotRepository.findById(dto.getTimeSlotId()).orElseThrow(() -> new RuntimeException("không tìm thấy timeslot")));

        return booking;
    }
}
