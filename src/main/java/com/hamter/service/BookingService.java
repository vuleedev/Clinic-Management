package com.hamter.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hamter.repository.BookingRepository;
import com.hamster.interfaceService.IBookingService;
import com.hamter.model.Bookings;
import com.hamter.service.EmailService;

@Service
public class BookingService implements IBookingService {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public List<Bookings> findAll() {
		return bookingRepository.findAll();
	}

	@Override
	public Bookings findById(Long id) {
		return bookingRepository.findById(id).orElse(null);
	}
	
	@Override
	public Bookings create(Bookings booking) {
		booking.setStatusId("PENDING");
		return bookingRepository.save(booking);
	}
	
	@Override
	public Bookings confirmBooking(Long id) {
		Bookings booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("không tìm thấy cuộc hẹn"));
		booking.setStatusId("CONFIRMED");
		return bookingRepository.save(booking);
	}
	
	@Override
	public Bookings cancelBooking(Long id) {
		Bookings booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("không tìm thấy cuộc hẹn"));
		booking.setStatusId("CANCEL");
		return bookingRepository.save(booking);
	}
	
	@Override
	public Bookings cancelBookingPending(Long id) {
		Bookings booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("không tìm thấy cuộc hẹn"));
		booking.getStatusId();
		return booking;
	}
	
	@Override
	@Scheduled(cron = "0 0/10 * * * *")
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hours = now.plus(2, ChronoUnit.HOURS);
        Date startDate = Timestamp.valueOf(now);
        Date endDate = Timestamp.valueOf(hours);

        List<Bookings> upcomingBookings = bookingRepository.findBookingsBetweenDates(startDate, endDate);

        for (Bookings booking : upcomingBookings) {
            String subject = "Nhắc nhở lịch hẹn khám bệnh";
            String body = "Xin chào, bạn có một lịch hẹn vào ngày: " + booking.getDate() + 
            			  " lúc " 									 + booking.getTimeType() + 
            			  ". Xin vui lòng đến đúng giờ!";
            emailService.SendMailBooking(booking.getEmail(), subject, body);
        }
    }
	
	@Override
	public Bookings update(Bookings booking) {
		if (bookingRepository.existsById(booking.getId())) {
			return bookingRepository.save(booking);
		}
		return new Bookings();
	}

	@Override
	public void delete(Long id) {
		bookingRepository.deleteById(id);
	}

}
