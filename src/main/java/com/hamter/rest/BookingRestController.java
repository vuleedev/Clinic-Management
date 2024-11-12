package com.hamter.rest;

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

import com.hamter.model.Booking;
import com.hamter.service.BookingService;
import com.hamter.service.EmailService;
import com.hamter.service.ScheduleService;


@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ScheduleService scheduleService;
    
    @Autowired
    private EmailService emailService;
    
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.findAll();
    }
    
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable("id") Long id) {
        return bookingService.findById(id);
    }
    
    @GetMapping("/available-times/{doctorId}")
    public ResponseEntity<List<String>> getAvailableTimes(@PathVariable Integer doctorId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<String> availableTimes = scheduleService.getAvailableTimesForDoctor(doctorId, date);
        return ResponseEntity.ok(availableTimes);
    }
    
    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
    	try {
            boolean isAvailable = scheduleService.isTimeSlotAvailable(
                booking.getDoctorId(),
                booking.getDate(),
                booking.getTimeType()
            );
            if (isAvailable) {
                bookingService.create(booking);
                return ResponseEntity.ok("Tạo cuộc hẹn thành công, chờ xác nhận. Nếu cuộc hẹn được xác nhận sẽ không thể hủy");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Bác sĩ bận, vui lòng tạo cuộc hẹn khác");
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Đã xảy ra lỗi khi tạo cuộc hẹn");
        }
        
    }
    
    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirmBooking(@PathVariable("id") Long id) {
        Booking confirmedBooking = bookingService.confirmBooking(id);
        String subject = "Thông báo về cuộc hẹn";
        String body = "Lịch hẹn đã được xác nhận. Ngày khám bệnh của bạn là " + confirmedBooking.getDate();
        emailService.SendMailBooking(confirmedBooking.getEmail(), subject, body);
        return ResponseEntity.ok("Cuộc hẹn đã được xác nhận và email đã được gửi");
    }
    
    //ADMIN
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") Long id, @RequestParam("reason") String reason) {
        Booking cancelBooking = bookingService.cancelBooking(id, reason);
        String subject = "Thông báo về cuộc hẹn";
        String body = "Cuộc hẹn của bạn đã bị hủy. Phòng khám đã từ chối cuộc hẹn với lý do: " + reason;
        emailService.SendMailBooking(cancelBooking.getEmail(), subject, body);
        return ResponseEntity.ok("Cuộc hẹn đã bị hủy");
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
    @DeleteMapping("/{id}")
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