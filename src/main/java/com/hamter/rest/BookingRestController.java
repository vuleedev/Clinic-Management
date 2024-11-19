package com.hamter.rest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.BookingDTO;
import com.hamter.dto.DoctorDTO;
import com.hamter.dto.TimeSlotDTO;
import com.hamter.mapper.DoctorMapper;
import com.hamter.mapper.TimeSlotMapper;
import com.hamter.model.Booking;
import com.hamter.model.Doctor;
import com.hamter.model.TimeSlot;
import com.hamter.service.BookingService;
import com.hamter.service.DoctorService;
import com.hamter.service.ScheduleService;
import com.hamter.service.TimeSlotService;


@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ScheduleService scheduleService;
    
    @Autowired
    private TimeSlotService timeSlotService;
    
    @Autowired
    private DoctorService doctorService;
    
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.findAll();
    }
    
    
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable("id") Long id) {
        return bookingService.findById(id);
    }
    
    
    @GetMapping("/by-specialty")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(@RequestParam Long specialtyId) {
        List<Doctor> doctors = doctorService.findDoctorsBySpecialty(specialtyId);
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        List<DoctorDTO> doctorDTOs = doctors.stream().map(DoctorMapper::toDTO) .collect(Collectors.toList());
        return ResponseEntity.ok(doctorDTOs);
    }
    
    
    @GetMapping("/available-times")
    public ResponseEntity<List<TimeSlotDTO>> getAvailableTimeSlots(@RequestParam Long doctorId,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<TimeSlot> availableTimeSlots = timeSlotService.findAvailableTimeSlots(doctorId, date);
        if (availableTimeSlots.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TimeSlotDTO> timeSlotDTOs = availableTimeSlots.stream().map(TimeSlotMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(timeSlotDTOs);
    }
    
    
    @PostMapping("/create-booking")
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            BookingDTO createdBookingDTO = bookingService.create(bookingDTO);
            return ResponseEntity.ok("Cuộc hẹn đã được tạo thành công, có thể hủy cuộc hẹn trước khi được xác nhận");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
    
    
    @PutMapping("/confirm/{id}")
    public ResponseEntity<String> confirmBooking(@PathVariable("id") Long id) {
    	try {
            Booking confirmedBooking = bookingService.confirmBooking(id);
            return ResponseEntity.ok("Cuộc hẹn đã được xác nhận và email đã được gửi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi, không thể xác nhận cuộc hẹn.");
        }
    }
    
    
    //ADMIN
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") Long id, @RequestParam("reason") String reason) {
    	try {
            Booking cancelBooking = bookingService.cancelBooking(id, reason);
            return ResponseEntity.ok("Cuộc hẹn đã bị hủy và email thông báo đã được gửi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi, không thể hủy cuộc hẹn.");
        }
    }
    
    
    @PutMapping("/complete/{id}")
    public ResponseEntity<String> completeBooking(@PathVariable("id") Long id) {
        Booking completedBooking = bookingService.completeBooking(id);
        if (completedBooking != null) {
            return ResponseEntity.ok("Cập nhật trạng thái cuộc hẹn thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuộc hẹn không tồn tại");
        }
    }
    
    
    @PutMapping("/not-attended/{id}")
    public ResponseEntity<String> notAttendedBooking(@PathVariable("id") Long id) {
        Booking completedBooking = bookingService.notAttendedBooking(id);
        if (completedBooking != null) {
            return ResponseEntity.ok("Cập nhật trạng thái cuộc hẹn thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuộc hẹn không tồn tại");
        }
    }
    
    
    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable("id") Long id, @RequestBody Booking booking) {
    	booking.setId(id);
        return bookingService.update(booking);
    }
    
    
    //CUSTOMERS
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") Long id) {
    	try {
            Booking booking = bookingService.cancelBookingPending(id);
            bookingService.delete(id);
            return ResponseEntity.ok("Xóa cuộc hẹn thành công");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy cuộc hẹn");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Đã xảy ra lỗi khi xóa cuộc hẹn");
        }
    }
}