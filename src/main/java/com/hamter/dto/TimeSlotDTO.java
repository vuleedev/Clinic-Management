package com.hamter.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TimeSlotDTO {
	
	private Long id;
    private Long doctorId;  
    private Long scheduleId;  
    private Date startTime;
    private Date endTime;
    private Boolean isAvailable;
    private Date createdAt;
    private Date updatedAt;
}
