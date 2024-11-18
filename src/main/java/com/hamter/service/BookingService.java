package com.hamter.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hamter.repository.BookingRepository;
import com.hamter.repository.TimeSlotRepository;
import com.hamter.model.Booking;
import com.hamter.model.TimeSlot;
import com.hamter.service.BookingService;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private TimeSlotRepository timeslotRepository;
	
	public List<Booking> findAll() {
		return bookingRepository.findAll();
	}
	
	public Booking findById(Long id) {
		return bookingRepository.findById(id).orElse(null);
	}
	
	public Booking create(Booking booking) {
		if (!canCreateNewBooking(booking.getPatientId())) {
            throw new IllegalStateException("Tạo cuộc hẹn thất bại. Bạn còn cuộc hẹn chưa khám, tạo cuộc hẹn khác sau");
        }
		TimeSlot timeSlot = timeslotRepository.findById(booking.getTimeSlot().getId())
		        .orElseThrow(() -> new IllegalStateException("TimeSlot không tồn tại."));
	    if (!timeSlot.getIsAvailable()) {
	        throw new IllegalStateException("Khung giờ này đã được đặt. Vui lòng chọn khung giờ khác.");
	    }

	    timeSlot.setIsAvailable(false);
	    timeslotRepository.save(timeSlot);
	    
        booking.setCreatedAt(new java.util.Date());
        booking.setUpdatedAt(new java.util.Date());
        booking.setStatusId("PENDING");
        
        String toEmail = "chiennhpd09284@fpt.edu.vn";  
        String subject = "Cuộc hẹn mới từ bệnh nhân";
        String body = "Bệnh nhân " + booking.getPatientId() + " đã tạo cuộc hẹn vào lúc " + booking.getCreatedAt() + ".\n" +
                      "Vui lòng xác nhận cuộc hẹn.";
        emailService.SendMailBooking(toEmail, subject, body);
        return bookingRepository.save(booking);
	}
	
	public boolean canCreateNewBooking(String patientId) {
		Optional<Booking> lastBooking = bookingRepository.findTopByPatientIdOrderByIdDesc(patientId);
	    if (lastBooking.isPresent()) {
	        Booking booking = lastBooking.get();
	        boolean isStatusValid = "COMPLETE".equals(booking.getStatusId()) || 
	                                "NOT_ATTENDED".equals(booking.getStatusId()) || 
	                                "CANCELED".equals(booking.getStatusId());
	        if (!isStatusValid) {
	            System.out.println("Tạo không thành công do trạng thái không hợp lệ");
	            return false;
	        }
	    }
	    return true;
    }
	
	public Booking confirmBooking(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("không tìm thấy cuộc hẹn"));
		booking.setStatusId("WAIT");
		booking = bookingRepository.save(booking);
		
        String subject = "Thông báo về cuộc hẹn";
        String body = "Lịch hẹn đã được xác nhận. Ngày khám bệnh của bạn là " + booking.getDate();
        emailService.SendMailBooking(booking.getEmail(), subject, body);
        return booking;
	}
	
	public Booking cancelBooking(Long id, String reason) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("không tìm thấy cuộc hẹn"));
		booking.setStatusId("CANCELED");
		booking.setCancelReason(reason);
		
		TimeSlot timeSlot = booking.getTimeSlot();
		timeSlot.setIsAvailable(true);
	    timeslotRepository.save(timeSlot);
	    
		String subject = "Thông báo về cuộc hẹn";
        String body = "Cuộc hẹn của bạn đã bị hủy. Phòng khám đã từ chối cuộc hẹn với lý do: " + reason;
        emailService.SendMailBooking(booking.getEmail(), subject, body);
		return bookingRepository.save(booking);
	}
	
	public Booking completeBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Booking updatedBooking = booking.get();
            updatedBooking.setStatusId("COMPLETE");
            updatedBooking.setUpdatedAt(new java.util.Date());
            return bookingRepository.save(updatedBooking);
        }
        return null;
    }
	
	public Booking notAttendedBooking(Long id) {
		Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Booking updatedBooking = booking.get();
            updatedBooking.setStatusId("NOT_ATTENDED");
            updatedBooking.setUpdatedAt(new java.util.Date());
            return bookingRepository.save(updatedBooking);
        }
        return null;
	}
	
	public Booking cancelBookingPending(Long id) {
		Booking booking = bookingRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hẹn"));
	    if (!"PENDING".equals(booking.getStatusId())) { 
	        throw new IllegalStateException("Chỉ có thể xóa cuộc hẹn có trạng thái chờ xác nhận");
	    }
	    TimeSlot timeSlot = booking.getTimeSlot();
	    timeSlot.setIsAvailable(false);
	    timeslotRepository.save(timeSlot);
	    
	    return booking;
	}
	
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
	
	public void checkStatusNotAttendedBooking(Long id, String statusId) {
		Optional<Booking> checkBooking = bookingRepository.findById(id);
        if (checkBooking.isPresent()) {
            Booking booking = checkBooking.get(); 
            booking.setStatusId(statusId);
            booking.setUpdatedAt(new Date());
            bookingRepository.save(booking);

            if ("NOT_ATTENDED".equals(statusId)) {
                int notAttendedCount = bookingRepository.countByPatientIdAndStatusId(booking.getPatientId(), "NOT_ATTENDED");
                if (notAttendedCount == 2) {
                    sendWarningEmail(booking);
                }
            }
        }
	}
	
	public void sendWarningEmail(Booking booking) {
		String subject = "Cảnh báo: Không tham gia cuộc hẹn khám bệnh";
        String body = "Chúng tôi xin thông báo rằng bạn đã không tham gia cuộc hẹn của mình 2 lần. "
        			   +"Nếu quá 3 lần tài khoản sẽ bị khóa";
        emailService.SendMailBooking(booking.getEmail(), subject, body);
	}
	
	public Booking update(Booking booking) {
		if (bookingRepository.existsById(booking.getId())) {
			return bookingRepository.save(booking);
		}
		return new Booking();
	}

	public void delete(Long id) {
		bookingRepository.deleteById(id);
	}

}
