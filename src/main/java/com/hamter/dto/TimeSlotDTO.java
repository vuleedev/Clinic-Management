package com.hamter.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TimeSlotDTO {

	private Long id;
    private Long scheduleId;
    private Date scheduleDate;
    private Long doctorId;
    private String doctorName;
    private String startTime;
    private String endTime;
    private Boolean isAvailable;
    private Date createdAt;
    private Date updatedAt;
}
