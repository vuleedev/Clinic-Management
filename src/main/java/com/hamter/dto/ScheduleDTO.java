package com.hamter.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ScheduleDTO {
	
	private Long id;
    private Integer currentNumber;
    private Integer maxNumber;
    private Date date;
    private String timeType;
    private Long doctorId; 
    private List<Long> timeSlotIds;  
    private Date createdAt;
    private Date updatedAt;
}
