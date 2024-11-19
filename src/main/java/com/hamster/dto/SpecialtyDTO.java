package com.hamster.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialtyDTO {
	private Long id;                  // ID chuyên khoa
    private String name;              // Tên chuyên khoa
    private String description;       // Mô tả về chuyên khoa
    private String image;             // Hình ảnh (có thể là URL)
    private Date createdAt;           // Thời gian tạo chuyên khoa
    private Date updatedAt;
}
