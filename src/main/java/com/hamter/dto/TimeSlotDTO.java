package com.hamter.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TimeSlotDTO {

	private Long id;
    private Long scheduleId;
    private Long DoctorId;
    private String startTime;
    private String endTime;
    private Boolean isAvailable;
    private Date createdAt;
    private Date updatedAt;
}
