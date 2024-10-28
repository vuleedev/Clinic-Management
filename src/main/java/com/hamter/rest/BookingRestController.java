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

import com.hamter.model.Booking;
import com.hamter.service.BookingService;
import com.hamter.service.ScheduleService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ScheduleService scheduleService;
    
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.findAll();
    }
    
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable("id") Long id) {
        return bookingService.findById(id);
    }
    
    //Dat lich chi khi co khung gio trong
    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        boolean isAvailable = scheduleService.isTimeSlotAvailable(
            booking.getDoctorId(),
            booking.getDate(), 
            booking.getTimeType()
        );
        if (isAvailable == true) {
            bookingService.update(booking);
            return ResponseEntity.ok("Booking successfully created.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
            	.body("Selected time slot is unavailable.");
        }
    }
    
    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable("id") Long id, @RequestBody Booking booking) {
    	booking.setId(id);
        return bookingService.update(booking);
    }
    
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable("id") Long id) {
    	bookingService.delete(id);
    }
}