package com.hamter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDTO {
	private Long id;              
    private Long doctorId;        
    private Long patientId;       
    private Long specialtyId;     
    private Integer totalCost;
}
