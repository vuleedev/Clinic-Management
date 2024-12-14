package com.hamter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties({"specialty", "clinic"})
public class DoctorBookingDTO {
	private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Boolean gender;
    private String profilePicture;
}
