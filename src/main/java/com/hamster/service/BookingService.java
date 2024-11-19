package com.hamster.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hamster.model.Bookings;
import com.hamster.model.TimeSlot;
import com.hamster.repository.BookingRepository;
import com.hamster.repository.TimeSlotRepository;
public class BookingService {
	@Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private TimeSlotRepository timeslotRepository;
    
    public List<Bookings> findAll() {
        return bookingRepository.findAll();
    }
    
    public Bookings findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }
    
    public Bookings create(Bookings booking) {
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
        
        booking.setCreatedAt(new Date());
        booking.setUpdatedAt(new Date());
        booking.setStatusId("PENDING");
        
        String toEmail = "chiennhpd09284@fpt.edu.vn";  
        String subject = "Cuộc hẹn mới từ bệnh nhân";
        String body = "Bệnh nhân " + booking.getPatientId() + " đã tạo cuộc hẹn vào lúc " + booking.getCreatedAt() + ".\n" +
                      "Vui lòng xác nhận cuộc hẹn.";
        emailService.SendMailBooking(toEmail, subject, body);
        return bookingRepository.save(booking);
    }
    public Bookings createBookingDesired(Bookings booking, String diseaseSymptoms, Date timeStart, Date timeEnd, Date date) {
        if (timeStart.after(timeEnd)) {
            throw new IllegalStateException("Giờ bắt đầu phải trước giờ kết thúc.");
        }
        
        String toEmail = "chiennhpd09284@fpt.edu.vn";  
        String subject = "Cuộc hẹn theo yêu cầu từ bệnh nhân";
        String body = "Bệnh nhân " + booking.getPatientId() + 
                      " đã tạo cuộc hẹn theo yêu cầu vào ngày " + date + 
                      ", từ " + timeStart + " đến " + timeEnd + 
                      ", triệu chứng bệnh" + diseaseSymptoms +
                      ". Vui lòng sắp xếp cuộc hẹn.";
        emailService.SendMailBooking(toEmail, subject, body);
        return bookingRepository.save(booking);
    }
    
    public boolean canCreateNewBooking(String patientId) {
        Optional<Bookings> lastBooking = bookingRepository.findTopByPatientIdOrderByIdDesc(patientId);
        if (lastBooking.isPresent()) {
            Bookings booking = lastBooking.get();
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
    
    public Bookings confirmBooking(Long id) {
        Bookings booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("không tìm thấy cuộc hẹn"));
        booking.setStatusId("WAIT");
        booking = bookingRepository.save(booking);
        
        String subject = "Thông báo về cuộc hẹn";
        String body = "Lịch hẹn đã được xác nhận. Ngày khám bệnh của bạn là " + booking.getDate();
        emailService.SendMailBooking(booking.getEmail(), subject, body);
        return booking;
    }
    
    public Bookings cancelBooking(Long id, String reason) {
        Bookings booking = bookingRepository.findById(id)
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
    
    public Bookings completeBooking(Long id) {
        Optional<Bookings> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Bookings updatedBooking = booking.get();
            updatedBooking.setStatusId("COMPLETE");
            updatedBooking.setUpdatedAt(new Date());
            return bookingRepository.save(updatedBooking);
        }
        return null;
    }
    
    public Bookings notAttendedBooking(Long id) {
        Optional<Bookings> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Bookings updatedBooking = booking.get();
            updatedBooking.setStatusId("NOT_ATTENDED");
            updatedBooking.setUpdatedAt(new Date());
            return bookingRepository.save(updatedBooking);
        }
        return null;
    }
    public Bookings cancelBookingPending(Long id) {
        Bookings booking = bookingRepository.findById(id)
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
        List<Bookings> upcomingBookings = bookingRepository.findBookingsBetweenDates(startDate, endDate);
        for (Bookings booking : upcomingBookings) {
            String subject = "Nhắc nhở lịch hẹn khám bệnh";
            String body = "Xin chào, bạn có một lịch hẹn vào ngày: " + booking.getDate() + 
                          " lúc " + booking.getTimeType() + 
                          ". Xin vui lòng đến đúng giờ!";
            emailService.SendMailBooking(booking.getEmail(), subject, body);
        }
    }
    
    public void checkStatusNotAttendedBooking(Long id, String statusId) {
        Optional<Bookings> checkBooking = bookingRepository.findById(id);
        if (checkBooking.isPresent()) {
            Bookings booking = checkBooking.get(); 
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
    
    public void sendWarningEmail(Bookings booking) {
        String subject = "Cảnh báo: Không tham gia cuộc hẹn khám bệnh";
        String body = "Chúng tôi xin thông báo rằng bạn đã không tham gia cuộc hẹn của mình 2 lần. "
                      + "Nếu quá 3 lần tài khoản sẽ bị khóa";
        emailService.SendMailBooking(booking.getEmail(), subject, body);
    }
    
    public Bookings update(Bookings booking) {
        if (bookingRepository.existsById(booking.getId())) {
            return bookingRepository.save(booking);
        }
        return new Bookings();
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }
}
