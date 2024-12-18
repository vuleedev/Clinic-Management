package com.hamter.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.BookingDTO;
import com.hamter.dto.DoctorDTO;
import com.hamter.dto.booking.ElementBookingDTO;
import com.hamter.mapper.BookingMapper;
import com.hamter.mapper.DoctorMapper;
import com.hamter.model.Booking;
import com.hamter.model.Doctor;
import com.hamter.service.BookingService;
import com.hamter.service.DoctorService;

import com.hamter.util.JwTokenUtil;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private JwTokenUtil jwTokenUtil;

	@GetMapping
	@PreAuthorize("hasAuthority('CUST')")
	public List<BookingDTO> getAllBookings() {
		List<Booking> bookings = bookingService.findAll(); 
		return bookings.stream() 
				.map(BookingMapper::toDTO).collect(Collectors.toList()); 

	}
	
	@GetMapping("/doctor/{doctorId}/booking")
    @PreAuthorize("hasAuthority('STAFF')")
    public List<BookingDTO> getBookingByDoctor(@PathVariable Long doctorId) {
        return bookingService.findBookingByDoctor(doctorId).stream()
            .map(BookingMapper::toDTO)
            .collect(Collectors.toList());
    }
	
	@GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable("id") Long id) {
        Booking booking = bookingService.findById(id);

        if (booking != null) {
            BookingDTO bookingDTO = BookingMapper.toDTO(booking);
            return ResponseEntity.ok(bookingDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

	@GetMapping("/doctors")
	@PreAuthorize("hasAuthority('CUST')")
	public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(@RequestParam Long specialtyId) {
		List<Doctor> doctors = doctorService.findDoctorsBySpecialty(specialtyId);
		if (doctors.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		List<DoctorDTO> doctorDTOs = doctors.stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok(doctorDTOs);
	}

	@GetMapping("/available-times")
	@PreAuthorize("hasAuthority('CUST')")
	public ResponseEntity<List<ElementBookingDTO>> getDoctorsWithAvailableTimes(@RequestParam Long specialtyId,
			@RequestParam Long doctorId, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		List<ElementBookingDTO> doctorsWithAvailableTimes = bookingService.getDoctorsWithAvailableTimes(specialtyId,
				doctorId, date);
		if (doctorsWithAvailableTimes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(doctorsWithAvailableTimes);

	}

	@PostMapping("/create-booking")
	@PreAuthorize("hasAuthority('CUST')")
	public ResponseEntity<Map<String, String>> createBooking(@RequestBody BookingDTO bookingDTO,
			@RequestHeader("Authorization") String authorizationHeader) {
		Long userId = getUserIdFromToken(authorizationHeader);
		try {
			bookingDTO.setUserId(userId);
			bookingService.create(bookingDTO, userId);
			Map<String, String> response = new HashMap<>();
			response.put("message", "Cuộc hẹn đã được tạo thành công, có thể hủy cuộc hẹn trước khi được xác nhận");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, String> response = new HashMap<>();
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		bookingService.delete(id);
	}
	
	@PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('CUST')")
    public BookingDTO updateBooking(@PathVariable("id") Long id, @RequestBody BookingDTO bookingDTO) {
    	bookingDTO.setId(id);
        return bookingService.update(id, bookingDTO);
    }
	
	@GetMapping("/all-booking/user")
    @PreAuthorize("hasAuthority('CUST')")
    public List<BookingDTO> getBookingByUserId(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = getUserIdFromToken(authorizationHeader);
        return bookingService.getBookingByUserId(userId);
    }
	
	@DeleteMapping("/booking/user/{id}")
	@PreAuthorize("hasAuthority('CUST')")
	public void deleteBookingByUser(@PathVariable("id") Long id) {
		bookingService.deleteBookingByUser(id);
	}
	
	@PutMapping("/update/bookings/{id}")
	@PreAuthorize("hasAuthority('STAFF')")
	public void updateStatusBooking(@PathVariable("id") Long id) {
		bookingService.updateStatusBooking(id);
	}
	
	
	private Long getUserIdFromToken(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String jwtToken = authorizationHeader.substring(7);
			return jwTokenUtil.extractUserId(jwtToken);
		}
		throw new RuntimeException("Không tìm thấy token");
	}
}