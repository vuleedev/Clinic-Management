package com.hamter.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDoctorDTO {
	
	private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Boolean gender;
    private String profilePicture;
    private Long specialtyId;
    private Date createdAt;
    private Date updatedAt; 
    private String password;
}
