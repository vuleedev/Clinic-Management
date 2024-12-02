package com.hamter.rest;

import java.time.LocalDate;
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
import com.hamter.dto.SpecialtyDTO;
import com.hamter.dto.booking.BookingStatusDTO;
import com.hamter.dto.booking.ElementBookingDTO;
import com.hamter.mapper.DoctorMapper;
import com.hamter.mapper.SpecialtyMapper;
import com.hamter.model.Booking;
import com.hamter.model.Doctor;
import com.hamter.model.Specialty;
import com.hamter.service.BookingService;
import com.hamter.service.DoctorService;
import com.hamter.service.SpecialtyService;
import com.hamter.util.JwTokenUtil;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private SpecialtyService specialtyService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private JwTokenUtil jwTokenUtil;
      
    @GetMapping
    @PreAuthorize("hasAuthority('MANAGE')")
    public List<Booking> getAllBookings() {
        return bookingService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE')")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") Long id) {
        Booking booking = bookingService.findById(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    @PutMapping("/booking/{id}/status")
    @PreAuthorize("hasAuthority('MANAGE')")
    public ResponseEntity<String> updateBookingStatus(@PathVariable("id") Long id, @RequestBody BookingStatusDTO request) {
        try {
            Booking updatedBooking = bookingService.updateBookingStatus(id, request); 
            if (updatedBooking != null) {
                return ResponseEntity.ok("Trạng thái cuộc hẹn đã được cập nhật thành công.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuộc hẹn không tồn tại.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi, không thể cập nhật trạng thái cuộc hẹn: " + e.getMessage());
        }
    }
    
    @GetMapping("/specialties")
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<List<SpecialtyDTO>> getAllSpecialties(@RequestHeader("Authorization") String authorizationHeader) {
    	Long userId = getUserIdFromToken(authorizationHeader);
    	List<Specialty> specialties = specialtyService.getAllSpecialties();
        if (specialties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<SpecialtyDTO> specialtyDTOs = specialties.stream()
                .map(SpecialtyMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(specialtyDTOs);
    }
    
    @GetMapping("/doctors")
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(@RequestParam Long specialtyId, @RequestHeader("Authorization") String authorizationHeader) {
    	Long userId = getUserIdFromToken(authorizationHeader);
    	List<Doctor> doctors = doctorService.findDoctorsBySpecialty(specialtyId);
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<DoctorDTO> doctorDTOs = doctors.stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(doctorDTOs);
    }
    
    @GetMapping("/available-times")
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<List<ElementBookingDTO>> getDoctorsWithAvailableTimes(@RequestHeader("Authorization") String authorizationHeader,@RequestParam Long specialtyId,@RequestParam Long doctorId,@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {  
        Long userId = getUserIdFromToken(authorizationHeader);
        List<ElementBookingDTO> doctorsWithAvailableTimes = bookingService.getDoctorsWithAvailableTimes(specialtyId, doctorId, date);   
        if (doctorsWithAvailableTimes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(doctorsWithAvailableTimes);
        
    }

    @PostMapping("/create-booking")
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<Map<String, String>> createBooking(@RequestBody BookingDTO bookingDTO, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = getUserIdFromToken(authorizationHeader);
        try {
            BookingDTO createdBookingDTO = bookingService.create(bookingDTO, userId);
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
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<Map<String, String>> deleteBooking(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = getUserIdFromToken(authorizationHeader); 
        Map<String, String> response = new HashMap<>();
        try {
            Booking booking = bookingService.cancelBookingPending(id, userId); 
            bookingService.delete(id);
            response.put("message", "Xóa cuộc hẹn thành công");
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (RuntimeException e) {
            response.put("message", "Không tìm thấy cuộc hẹn");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi khi xóa cuộc hẹn");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private Long getUserIdFromToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            return jwTokenUtil.extractUserId(jwtToken);
        }
        throw new RuntimeException("Không tìm thấy token");
    }
}