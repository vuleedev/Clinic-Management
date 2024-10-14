package com.hamter.dto;

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
    private Long doctorId;           
    private Long specialtyId;        
    private Long clinicId;           
    private String priceId;          
    private Long provinceId;         
    private String paymentId;        
    private String addressClinic;    
    private String nameClinic;       
    private String note;             
    private Integer count;
}
