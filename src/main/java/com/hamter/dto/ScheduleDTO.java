package com.hamter.dto;

import java.util.Date;

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
    private Date date;           
    private String timeType;       
    private Long doctorId;
    private java.util.Date createdAt;        
    private java.util.Date updatedAt;  
}
