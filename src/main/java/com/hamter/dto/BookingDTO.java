package com.hamter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {
	private Long id;                     
    private String statusId;             
    private Long doctorId;               
    private Long patientId;              
    private String date;                 
    private String timeType;             
    private String token;                 
    private byte[] imageRemedy;          
    private String patientName;          
    private String patientPhoneNumber;  
    private String patientAddress;       
    private String patientReason;       
    private String patientGender;       
    private String patientBirthday;
}
