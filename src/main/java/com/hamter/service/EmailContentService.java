package com.hamter.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.hamter.dto.BookingDTO;
import com.hamter.dto.email.EmailDTO;
import com.hamter.model.Booking;

@Service
public class EmailContentService {
	public EmailDTO getNewBookingEmailContent(BookingDTO bookingDTO, Date createdAt) {
        String subject = "Cuộc hẹn mới từ bệnh nhân";
        String body = String.format("Bệnh nhân với ID %d đã tạo cuộc hẹn vào lúc %s. Vui lòng xác nhận cuộc hẹn.",
                                    bookingDTO.getUserId(), createdAt.toString());
        return new EmailDTO(subject, body);
    }
	public EmailDTO getReminderEmailContent(Booking booking) {
        String subject = "Nhắc nhở lịch hẹn khám bệnh";
        String body = String.format("Xin chào, bạn có một lịch hẹn vào ngày: %s lúc %s. Xin vui lòng đến đúng giờ!",
                                    booking.getDate());
        return new EmailDTO(subject, body);
    }

    public EmailDTO getCancelBookingEmailContent(Booking booking, String reason) {
        String subject = "Thông báo về cuộc hẹn";
        String body = String.format("Cuộc hẹn của bạn đã bị hủy. Phòng khám đã từ chối cuộc hẹn với lý do: %s", reason);
        return new EmailDTO(subject, body);
    }

    public EmailDTO getConfirmBookingEmailContent(Booking booking) {
        String subject = "Thông báo về cuộc hẹn";
        String body = String.format("Lịch hẹn đã được xác nhận. Ngày khám bệnh của bạn là %s", booking.getDate());
        return new EmailDTO(subject, body);
    }

    public EmailDTO getCompleteBookingEmailContent() {
        String subject = "Thông báo về trạng thái cuộc hẹn";
        String body = "Cuộc hẹn của bạn đã hoàn thành.";
        return new EmailDTO(subject, body);
    }

    public EmailDTO getNotAttendedBookingEmailContent() {
        String subject = "Thông báo về trạng thái cuộc hẹn";
        String body = "Cuộc hẹn của bạn không được tham dự.";
        return new EmailDTO(subject, body);
    }

    public EmailDTO getWarningEmailContent() {
        String subject = "Cảnh báo: Không tham gia cuộc hẹn khám bệnh";
        String body = "Chúng tôi xin thông báo rằng bạn đã không tham gia cuộc hẹn của mình 2 lần. "
                       + "Nếu quá 3 lần tài khoản sẽ bị khóa.";
        return new EmailDTO(subject, body);
    }
}
