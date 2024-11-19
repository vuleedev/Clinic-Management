package com.hamter.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DoctorDTO {

	private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private String profilePicture;
    private Long specialtyId;  
    private Date createdAt;
    private Date updatedAt;
}
