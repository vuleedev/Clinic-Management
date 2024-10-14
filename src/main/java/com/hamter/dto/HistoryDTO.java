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
	private Long id;              
    private Long patientId;       
    private Long doctorId;        
    private String description;   
    private String drugs;         
    private byte[] files;         
    private String reason;
}
