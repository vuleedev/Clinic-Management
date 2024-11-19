package com.hamster.controller;

import java.util.Date;
import java.util.List;

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

import com.hamster.interfaceService.IBookingService;
import com.hamster.model.Bookings;
import com.hamster.model.TimeSlot;
import com.hamster.service.BookingService;
import com.hamster.service.EmailService;
import com.hamster.service.ScheduleService;
import com.hamster.service.TimeSlotService;


@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/bookings")
public class BookingController {

	@Autowired
    private BookingService bookingService;
    
    @Autowired
    private TimeSlotService timeSlotService;
    
    @GetMapping
    public List<Bookings> getAllBookings() {
        return bookingService.findAll();
    }
    
    @GetMapping("/{id}")
    public Bookings getBookingById(@PathVariable("id") Long id) {
        return bookingService.findById(id);
    }
    
    @GetMapping("/available-times")
    public ResponseEntity<List<TimeSlot>> getAvailableTimeSlots(@RequestParam Integer doctorId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<TimeSlot> availableTimeSlots = timeSlotService.findAvailableTimeSlots(doctorId, date);
        if (availableTimeSlots.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(availableTimeSlots);
    }
    
    @PostMapping("/create-booking")
    public ResponseEntity<String> createBooking(@RequestBody Bookings booking) {
    	try {
    		Bookings createBooking = bookingService.create(booking);
            return ResponseEntity.ok("Tạo cuộc hẹn thành công, chờ xác nhận. Nếu cuộc hẹn được xác nhận sẽ không thể hủy");  	
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Đã xảy ra lỗi khi tạo cuộc hẹn " + e);
        }
        
    }
    
    @PostMapping("/create-booking-desired")
    public ResponseEntity<String> createBookingRequest(
            @RequestBody Bookings booking,
            @RequestParam("diseaseSymptoms") String diseaseSymptoms, @RequestParam("timeStart") @DateTimeFormat(pattern = "HH:mm") Date timeStart, 
            @RequestParam("timeEnd") @DateTimeFormat(pattern = "HH:mm") Date timeEnd, 
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            Bookings createdBooking = bookingService.createBookingDesired(booking, diseaseSymptoms, timeStart, timeEnd, date);
            return ResponseEntity.ok("Bạn đã tạo cuộc hẹn theo yêu cầu, chờ phòng khám sắp xếp cuộc hẹn.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Đã xảy ra lỗi khi tạo cuộc hẹn: " + e.getMessage());
        }
    }
    
    @PutMapping("/confirm/{id}")
    public ResponseEntity<String> confirmBooking(@PathVariable("id") Long id) {
    	try {
            Bookings confirmedBooking = bookingService.confirmBooking(id);
            return ResponseEntity.ok("Cuộc hẹn đã được xác nhận và email đã được gửi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi, không thể xác nhận cuộc hẹn.");
        }
    }
    
    //ADMIN
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") Long id, @RequestParam("reason") String reason) {
    	try {
            Bookings cancelBooking = bookingService.cancelBooking(id, reason);
            return ResponseEntity.ok("Cuộc hẹn đã bị hủy và email thông báo đã được gửi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi, không thể hủy cuộc hẹn.");
        }
    }
    
    @PutMapping("/complete/{id}")
    public ResponseEntity<String> completeBooking(@PathVariable("id") Long id) {
        Bookings completedBooking = bookingService.completeBooking(id);
        if (completedBooking != null) {
            return ResponseEntity.ok("Cập nhật trạng thái cuộc hẹn thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuộc hẹn không tồn tại");
        }
    }
    
    @PutMapping("/not-attended/{id}")
    public ResponseEntity<String> notAttendedBooking(@PathVariable("id") Long id) {
        Bookings completedBooking = bookingService.notAttendedBooking(id);
        if (completedBooking != null) {
            return ResponseEntity.ok("Cập nhật trạng thái cuộc hẹn thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuộc hẹn không tồn tại");
        }
    }
    
    @PutMapping("/{id}")
    public Bookings updateBooking(@PathVariable("id") Long id, @RequestBody Bookings booking) {
    	booking.setId(id);
    	return bookingService.update(booking);
    }
    
    //CUSTOMERS
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") Long id) {
    	try {
            Bookings booking = bookingService.cancelBookingPending(id);
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
