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
public class DoctorInfoDTO {
	private Long id;  
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private String gender;
	
	private String profilePicture;

    private Date createdAt;

    private Date updatedAt;

    private Long specialtyId; // ID của Specialty (nếu cần)

    private String specialtyName; // Tên của Specialty (nếu cần)
}
