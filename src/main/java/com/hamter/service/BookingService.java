package com.hamter.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hamter.dto.BookingDTO;
import com.hamter.dto.DoctorDTO;
import com.hamter.dto.TimeSlotDTO;
import com.hamter.dto.booking.BookingDetailsDTO;
import com.hamter.dto.booking.BookingStatusDTO;
import com.hamter.dto.booking.ElementBookingDTO;
import com.hamter.dto.email.EmailDTO;
import com.hamter.mapper.BookingMapper;
import com.hamter.mapper.DoctorMapper;
import com.hamter.mapper.TimeSlotMapper;
import com.hamter.model.Booking;
import com.hamter.model.Doctor;
import com.hamter.model.TimeSlot;
import com.hamter.model.User;
import com.hamter.repository.BookingRepository;
import com.hamter.repository.DoctorRepository;
import com.hamter.repository.TimeSlotRepository;
import com.hamter.repository.UserRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
    private DoctorRepository doctorRepository;

	@Autowired
	private TimeSlotService timeSlotService;

	@Autowired
    private TimeSlotRepository timeSlotRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailContentService emailContentService;

	@Autowired
	private DoctorService doctorService;

	public List<Booking> findAll() {
	    return bookingRepository.findAll();
	}

	public Booking findById(Long id) {
	    return bookingRepository.findById(id).orElse(null);
	}
	
	

	public List<ElementBookingDTO> getDoctorsWithAvailableTimes(Long specialtyId, Long doctorId, Date date) {
        List<Doctor> doctors = doctorService.findDoctorsBySpecialty(specialtyId);
        if (doctors.isEmpty()) {
            return Collections.emptyList();
        }
        List<TimeSlot> availableTimeSlots = timeSlotService.findAvailableTimeSlots(doctorId, date);
        List<TimeSlotDTO> timeSlotDTOs = availableTimeSlots.stream()
                .map(TimeSlotMapper::toDTO)
                .collect(Collectors.toList());
        return doctors.stream()
                .map(doctor -> {
                    DoctorDTO doctorDTO = DoctorMapper.toDTO(doctor);
                    List<TimeSlotDTO> doctorTimeSlots = timeSlotDTOs.stream()
                            .filter(timeSlotDTO -> timeSlotDTO.getDoctorId().equals(doctor.getId()))
                            .collect(Collectors.toList());
                    return new ElementBookingDTO(doctorDTO, doctorTimeSlots);
                })
                .collect(Collectors.toList());
    }

	public BookingDTO create(BookingDTO bookingDTO, Long userId) {
	    Optional<User> patientOpt = userRepository.findById(userId);
	    if (!patientOpt.isPresent()) {
	        throw new IllegalStateException("Bệnh nhân không tồn tại.");
	    }
	    if (!canCreateNewBooking(userId)) {
	        throw new IllegalStateException("Tạo cuộc hẹn thất bại. Bạn còn cuộc hẹn chưa khám, tạo cuộc hẹn khác sau");
	    }
	    TimeSlot timeSlot = timeSlotRepository.findById(bookingDTO.getTimeSlotId())
	            .orElseThrow(() -> new IllegalStateException("Khung giờ không tồn tại."));
	    if (!timeSlot.getIsAvailable()) {
	        throw new IllegalStateException("Khung giờ này đã được đặt. Vui lòng chọn khung giờ khác.");
	    }
	    timeSlot.setIsAvailable(false);
	    timeSlotRepository.save(timeSlot);
	    Booking booking = BookingMapper.toEntity(bookingDTO, doctorRepository, timeSlotRepository, userRepository);
	    booking.setCreatedAt(new Date());
	    booking.setUpdatedAt(new Date());
	    booking.setStatusId("PENDING");
	    EmailDTO emailContent = emailContentService.getNewBookingEmailContent(bookingDTO, booking.getCreatedAt());
	    String toEmail = "chiennhpd09284@fpt.edu.vn";
	    emailService.SendMailBooking(toEmail, emailContent);
	    Booking savedBooking = bookingRepository.save(booking);
	    return BookingMapper.toDTO(savedBooking);
	}

    public boolean canCreateNewBooking(Long userId) {
        Optional<Booking> lastBooking = bookingRepository.findTopByUserIdOrderByIdDesc(userId);
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

    public Booking updateBookingStatus(Long id, BookingStatusDTO request) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hẹn"));
        booking.setStatusId(request.getStatus());
        if ("CANCELED".equalsIgnoreCase(request.getStatus())) {
            booking.setCancelReason(request.getReason());
            TimeSlot timeSlot = booking.getTimeSlot();
            timeSlot.setIsAvailable(true);
            timeSlotRepository.save(timeSlot);
            EmailDTO emailContent = emailContentService.getCancelBookingEmailContent(booking, request.getReason());
            //emailService.SendMailBooking(booking.getEmail(), emailContent);
        }
        if ("WAIT".equalsIgnoreCase(request.getStatus())) {
            EmailDTO emailContent = emailContentService.getConfirmBookingEmailContent(booking);
            //emailService.SendMailBooking(booking.getEmail(), emailContent);
        } else if ("COMPLETE".equalsIgnoreCase(request.getStatus())) {
            EmailDTO emailContent = emailContentService.getCompleteBookingEmailContent();
            //emailService.SendMailBooking(booking.getEmail(), emailContent);
        } else if ("NOT_ATTENDED".equalsIgnoreCase(request.getStatus())) {
            EmailDTO emailContent = emailContentService.getNotAttendedBookingEmailContent();
            //emailService.SendMailBooking(booking.getEmail(), emailContent);
        }
        booking.setUpdatedAt(new Date());
        return bookingRepository.save(booking);
    }

    @Scheduled(cron = "0 0/10 * * * *")
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hours = now.plus(2, ChronoUnit.HOURS);
        Date startDate = Timestamp.valueOf(now);
        Date endDate = Timestamp.valueOf(hours);
        List<Booking> upcomingBookings = bookingRepository.findBookingsBetweenDates(startDate, endDate);
        for (Booking booking : upcomingBookings) {
            EmailDTO emailContent = emailContentService.getReminderEmailContent(booking);
            //emailService.SendMailBooking(booking.getEmail(), emailContent);
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
                Long userId = booking.getUser().getId();
                int notAttendedCount = bookingRepository.countByUserIdAndStatusId(userId, "NOT_ATTENDED");
                if (notAttendedCount == 2) {
                    EmailDTO emailContent = emailContentService.getWarningEmailContent();
                    //emailService.SendMailBooking(booking.getEmail(), emailContent);
                }
            }
        }
    }

    public Booking cancelBookingPending(Long id, Long userId) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hẹn"));
        if (!"PENDING".equals(booking.getStatusId())) {
            throw new RuntimeException("Cuộc hẹn không thể hủy");
        }
        booking.setStatusId("CANCELED");
        TimeSlot timeSlot = booking.getTimeSlot();
        timeSlot.setIsAvailable(true);
        timeSlotRepository.save(timeSlot);
        return bookingRepository.save(booking);
    }

	public Booking update(Booking booking, Long userId) {
		if (bookingRepository.existsById(booking.getId())) {
			return bookingRepository.save(booking);
		}
		return new Booking();
	}

	public void delete(Long id) {
	    if (!bookingRepository.existsById(id)) {
	        throw new RuntimeException("Không tìm thấy cuộc hẹn");
	    }
	    bookingRepository.deleteById(id);
	}
}
