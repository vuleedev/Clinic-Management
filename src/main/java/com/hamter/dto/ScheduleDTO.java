package com.hamter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO {
	private Long id;               
    private Integer currentNumber; 
    private Integer maxNumber;     
    private String date;           
    private String timeType;       
    private Long doctorId;
}
