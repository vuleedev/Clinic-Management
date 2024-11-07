package com.hamter.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicDTO {
	private Long id;             // ID của phòng khám
    private String name;         // Tên phòng khám
    private String address;      // Địa chỉ phòng khám
    private String description;  // Mô tả phòng khám
    private String image;        // Ảnh đại diện phòng khám
    private Date createdAt;      // Ngày tạo
    private Date updatedAt;  
}
