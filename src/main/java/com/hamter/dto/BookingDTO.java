package com.hamter.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {
	private Long id;              // ID của booking
    private String statusId;      // ID trạng thái của booking
    private Integer doctorId;     // ID của bác sĩ
    private String patientId;     // ID của bệnh nhân
    private Date date;            // Ngày đặt lịch, kiểu Date (có thể đổi thành String nếu cần định dạng lại)
    private String timeType;      // Loại thời gian (sáng, chiều, ...)
    private Date createdAt;       // Ngày tạo
    private Date updatedAt;       // Ngày cập nhật
}
