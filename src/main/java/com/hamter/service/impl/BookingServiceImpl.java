package com.hamter.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hamter.repository.BookingRepository;
import com.hamter.repository.ScheduleRepository;
import com.hamter.model.Booking;
import com.hamter.service.BookingService;
import com.hamter.service.EmailService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public List<Booking> findAll() {
		return bookingRepository.findAll();
	}
	
	@Override
	public Booking findById(Long id) {
		return bookingRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public Booking create(Booking booking) {
		if (!canCreateNewBooking(booking.getPatientId())) {
            throw new IllegalStateException("Tạo cuộc hẹn thất bại. Bạn còn cuộc hẹn chưa khám, tạo cuộc hẹn khác sau");
        }
        booking.setCreatedAt(new java.util.Date());
        booking.setUpdatedAt(new java.util.Date());
        booking.setStatusId("PENDING");
        booking.setStatus2Id("WAIT");
        return bookingRepository.save(booking);
	}
	
	@Override
	public boolean canCreateNewBooking(String patientId) {
        Optional<Booking> lastBooking = bookingRepository.findTopByPatientIdOrderByIdDesc(patientId);
        if (lastBooking.isPresent()) {
            Booking booking = lastBooking.get();
            boolean isStatusValid = "CONFIRMED".equals(booking.getStatusId()) || "CANCELED".equals(booking.getStatusId());
            boolean isStatus2Valid = "COMPLETE".equals(booking.getStatus2Id()) || "NOT_ATTENDED".equals(booking.getStatus2Id());
            if (!isStatusValid || !isStatus2Valid) {
                System.out.println("tạo không thành công");
                return false;
            }
        }
        return true;
    }
	
	@Override
	public Booking completeBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Booking updatedBooking = booking.get();
            updatedBooking.setStatus2Id("COMPLETE");
            updatedBooking.setUpdatedAt(new java.util.Date());
            return bookingRepository.save(updatedBooking);
        }
        return null;
    }
	
	@Override
	public Booking notAttendedBooking(Long id) {
		Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Booking updatedBooking = booking.get();
            updatedBooking.setStatus2Id("NOT_ATTENDED");
            updatedBooking.setUpdatedAt(new java.util.Date());
            return bookingRepository.save(updatedBooking);
        }
        return null;
	}
	
	@Override
	public Booking confirmBooking(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("không tìm thấy cuộc hẹn"));
		booking.setStatusId("CONFIRMED");
		booking.setStatus2Id("WAIT");
		return bookingRepository.save(booking);
	}
	
	@Override
	public Booking cancelBooking(Long id, String reason) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("không tìm thấy cuộc hẹn"));
		booking.setStatusId("CANCELED");
		booking.setCancelReason(reason);
		return bookingRepository.save(booking);
	}
	
	@Override
	public Booking cancelBookingPending(Long id) {
		Booking booking = bookingRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("không tìm thấy cuộc hẹn"));
		    if ("CONFIRMED".equals(booking.getStatusId())) {
		        throw new IllegalStateException("Không thể xóa cuộc hẹn đã xác nhận");
		    }
		    return booking;
	}
	
	@Override
	@Scheduled(cron = "0 0/10 * * * *")
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hours = now.plus(2, ChronoUnit.HOURS);
        Date startDate = Timestamp.valueOf(now);
        Date endDate = Timestamp.valueOf(hours);
        List<Booking> upcomingBookings = bookingRepository.findBookingsBetweenDates(startDate, endDate);
        for (Booking booking : upcomingBookings) {
            String subject = "Nhắc nhở lịch hẹn khám bệnh";
            String body = "Xin chào, bạn có một lịch hẹn vào ngày: " + booking.getDate() + 
            			  " lúc " 									 + booking.getTimeType() + 
            			  ". Xin vui lòng đến đúng giờ!";
            emailService.SendMailBooking(booking.getEmail(), subject, body);
        }
    }
	
	@Override
	public void checkStatusNotAttendedBooking(Long id, String status2Id) {
		Optional<Booking> checkBooking = bookingRepository.findById(id);
        if (checkBooking.isPresent()) {
            Booking booking = checkBooking.get(); 
            booking.setStatus2Id(status2Id);
            booking.setUpdatedAt(new Date());
            bookingRepository.save(booking);

            if ("NOT_ATTENDED".equals(status2Id)) {
                int notAttendedCount = bookingRepository.countByPatientIdAndStatus2Id(booking.getPatientId(), "NOT_ATTENDED");
                if (notAttendedCount >= 2) {
                    sendWarningEmail(booking);
                }
            }
        }
	}
	
	@Override
	public void sendWarningEmail(Booking booking) {
		String subject = "Cảnh báo: Không tham gia cuộc hẹn khám bệnh";
        String message = "Chúng tôi xin thông báo rằng bạn đã không tham gia cuộc hẹn của mình 2 lần. "
        			   + "Nếu quá 3 lần tài khoản sẽ bị khóa";
        emailService.SendMailBooking(booking.getEmail(), subject, message);
	}
	
	@Override
	public Booking update(Booking booking) {
		if (bookingRepository.existsById(booking.getId())) {
			return bookingRepository.save(booking);
		}
		return new Booking();
	}

	@Override
	public void delete(Long id) {
		bookingRepository.deleteById(id);
	}

}
