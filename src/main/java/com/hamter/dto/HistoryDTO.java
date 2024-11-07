package com.hamter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDTO {
	 private Long id;                  // ID của lịch sử
	    private Integer patientId;        // ID bệnh nhân
	    private Integer doctorId;         // ID bác sĩ
	    private String description;       // Mô tả về bệnh án
	    private String files;             // Dữ liệu file (nếu có)
	    private java.util.Date createdAt;           // Thời gian tạo lịch sử
	    private java.util.Date updatedAt;
}
