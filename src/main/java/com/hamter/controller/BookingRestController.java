package com.hamter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.hamster.interfaceService.IBookingService;
import com.hamter.model.Booking;
import com.hamter.model.Bookings;
import com.hamter.service.EmailService;
import com.hamter.service.ScheduleService;


@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/bookings")
public class BookingRestController {

    @Autowired
    private IBookingService bookingService;
    
    @Autowired
    private ScheduleService scheduleService;
    
    @Autowired
    private EmailService emailService;
    
    @GetMapping
    public List<Bookings> getAllBookings() {
        return bookingService.findAll();
    }
    
    @GetMapping("/{id}")
    public Bookings getBookingById(@PathVariable("id") Long id) {
        return bookingService.findById(id);
    }
    
    @PostMapping
<<<<<<< HEAD
    public ResponseEntity<String> createBooking(@RequestBody Bookings booking) {
        boolean isAvailable = scheduleService.isTimeSlotAvailable(
            booking.getDoctorId(),
            booking.getDate(), 
            booking.getTimeType()
        );
        if (isAvailable == true) {
        	bookingService.create(booking);
            return ResponseEntity.ok("Tạo cuộc hẹn thành công, chờ xác nhận. Nếu cuộc hẹn được xác nhận sẽ không thể hủy");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
            	.body("Bác sĩ bận, vui lòng tạo cuộc hẹn khác");
=======
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
>>>>>>> 5fb944f1f4a053db8ba3b8c811043d66612133cb
        }
        
    }
    
    @PostMapping("/confirm/{id}")
<<<<<<< HEAD
    public ResponseEntity<String> confirmBooking(@PathVariable Long id) {
        Bookings confirmedBooking = bookingService.confirmBooking(id);
=======
    public ResponseEntity<String> confirmBooking(@PathVariable("id") Long id) {
        Booking confirmedBooking = bookingService.confirmBooking(id);
>>>>>>> 5fb944f1f4a053db8ba3b8c811043d66612133cb
        String subject = "Thông báo về cuộc hẹn";
        String body = "Lịch hẹn đã được xác nhận. Ngày khám bệnh của bạn là " + confirmedBooking.getDate();
        emailService.SendMailBooking(confirmedBooking.getEmail(), subject, body);
        return ResponseEntity.ok("Cuộc hẹn đã được xác nhận và email đã được gửi");
    }
    
    //ADMIN
    @PostMapping("/cancel/{id}")
<<<<<<< HEAD
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        Bookings cancelBooking = bookingService.cancelBooking(id);
=======
    public ResponseEntity<String> cancelBooking(@PathVariable("id") Long id) {
        Booking cancelBooking = bookingService.cancelBooking(id);
>>>>>>> 5fb944f1f4a053db8ba3b8c811043d66612133cb
        String subject = "Thông báo về cuộc hẹn";
        String body = "cuộc hẹn của bạn đã bị hủy, phòng khám đã từ chối cuộc hẹn";
        emailService.SendMailBooking(cancelBooking.getEmail(), subject, body);
        return ResponseEntity.ok("Cuộc hẹn đã bị hủy");
    }
    
    @PutMapping("/{id}")
    public Bookings updateBooking(@PathVariable("id") Long id, @RequestBody Bookings booking) {
    	booking.setId(id);
        return bookingService.update(booking);
    }
    
    @PutMapping("/complete/{id}")
    public ResponseEntity<String> completeBooking(@PathVariable("id") Long id) {
        Booking completedBooking = bookingService.completeBooking(id);
        if (completedBooking != null) {
            return ResponseEntity.ok("Cuộc hẹn đã được hoàn thành");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuộc hẹn không tồn tại");
        }
    }
    
    //CUSTOMERS
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") Long id) {
<<<<<<< HEAD
        Bookings booking = bookingService.cancelBookingPending(id);
        if (booking.getStatusId().equals("CONFIRMED")) {
            throw new IllegalStateException("Không thể xóa cuộc hẹn đã xác nhận");
=======
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
>>>>>>> 5fb944f1f4a053db8ba3b8c811043d66612133cb
        }
    }
}
