package com.hamter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.DoctorBookingDTO;
import com.hamter.dto.TimeSlotDTO;
import com.hamter.dto.UserDTO;
import com.hamter.mapper.TimeSlotMapper;
import com.hamter.mapper.UserMapper;
import com.hamter.model.Booking;
import com.hamter.model.Doctor;
import com.hamter.model.TimeSlot;
import com.hamter.model.User;
import com.hamter.service.BookingService;

@RestController
@RequestMapping("/api/bookings-details")
public class BookingDetailsController {
	
	@Autowired
	private BookingService bookingService;
	
	@PreAuthorize("hasAuthority('CUST')")
	@GetMapping("/{bookingId}/doctor")
	public ResponseEntity<DoctorBookingDTO> getDoctorByBookingId(@PathVariable Long bookingId) {
	    Booking booking = bookingService.findById(bookingId);
	    if (booking == null) {
	        return ResponseEntity.notFound().build();
	    }
	    Doctor doctor = booking.getDoctor();
	    if (doctor == null) {
	        return ResponseEntity.notFound().build();
	    }
	    DoctorBookingDTO doctorBookingDTO = new DoctorBookingDTO();
	    doctorBookingDTO.setId(doctor.getId());
	    doctorBookingDTO.setName(doctor.getName());
	    doctorBookingDTO.setEmail(doctor.getEmail());
	    doctorBookingDTO.setPhoneNumber(doctor.getPhoneNumber());
	    doctorBookingDTO.setGender(doctor.getGender());
	    doctorBookingDTO.setProfilePicture(doctor.getProfilePicture());

	    return ResponseEntity.ok(doctorBookingDTO);
	}

	
	@PreAuthorize("hasAuthority('CUST')")
	@GetMapping("/{bookingId}/timeslot")
	public ResponseEntity<TimeSlotDTO> getTimeSlotByBookingId(@PathVariable Long bookingId) {
	    Booking booking = bookingService.findById(bookingId);
	    if (booking == null) {
	        return ResponseEntity.notFound().build();
	    }
	    TimeSlot timeSlot = booking.getTimeSlot();
	    if (timeSlot == null) {
	        return ResponseEntity.notFound().build();
	    }
	    TimeSlotDTO timeSlotDTO = TimeSlotMapper.toDTO(timeSlot);
	    return ResponseEntity.ok(timeSlotDTO);
	}
	
	@PreAuthorize("hasAuthority('CUST')")
	@GetMapping("/{bookingId}/user")
	public ResponseEntity<UserDTO> getUserByBookingId(@PathVariable Long bookingId) {
	    Booking booking = bookingService.findById(bookingId);
	    if (booking == null) {
	        return ResponseEntity.notFound().build();
	    }
	    User user = booking.getUser();
	    if (user == null) {
	        return ResponseEntity.notFound().build();
	    }
	    UserDTO userDTO = UserMapper.toDTO(user);
	    return ResponseEntity.ok(userDTO); 
	}

}
