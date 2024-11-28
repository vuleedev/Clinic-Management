package com.hamter.rest;

import java.util.Date;
import java.util.List;

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
import com.hamter.dto.BookingStatusDTO;
import com.hamter.dto.ElementBookingDTO;
import com.hamter.model.Booking;
import com.hamter.service.BookingService;
import com.hamter.service.DoctorService;
import com.hamter.util.JwTokenUtil;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private JwTokenUtil jwTokenUtil;
      
    @GetMapping
    @PreAuthorize("hasRole('MANAGE')")
    public List<Booking> getAllBookings() {
        return bookingService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGE')")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") Long id) {
        Booking booking = bookingService.findById(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    @PutMapping("/booking/{id}/status")
    @PreAuthorize("hasRole('MANAGE')")
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

    @GetMapping("/doctorsWithAvailableTimes")
    @PreAuthorize("hasRole('CUST')")
    public ResponseEntity<List<ElementBookingDTO>> getDoctorsWithAvailableTimes(
    		@RequestHeader("Authorization") String authorizationHeader,
            @RequestParam Long specialtyId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam Date date) {
    	Long userId = getUserIdFromToken(authorizationHeader);
        List<ElementBookingDTO> doctorsWithAvailableTimes = bookingService.getDoctorsWithAvailableTimes(specialtyId, doctorId, date);   
        if (doctorsWithAvailableTimes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(doctorsWithAvailableTimes);
    }
    
    @PostMapping("/create-booking")
    @PreAuthorize("hasRole('CUST')")
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO bookingDTO, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = getUserIdFromToken(authorizationHeader);
        try {
            BookingDTO createdBookingDTO = bookingService.create(bookingDTO, userId);
            return ResponseEntity.ok("Cuộc hẹn đã được tạo thành công, có thể hủy cuộc hẹn trước khi được xác nhận");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('CUST')")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = getUserIdFromToken(authorizationHeader); 
        try {
            Booking booking = bookingService.cancelBookingPending(id, userId);  
            bookingService.delete(id);  
            return ResponseEntity.ok("Xóa cuộc hẹn thành công");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy cuộc hẹn");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi xóa cuộc hẹn");
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