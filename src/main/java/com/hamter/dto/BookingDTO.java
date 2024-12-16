package com.hamter.dto;

import java.time.LocalTime;
import java.util.Date;

import lombok.Data;

@Data
public class BookingDTO {

	private Long id;
    private String statusId;
    private Long doctorId;
    private String doctorName;
    private Long userId;
    private String userName;
    private String cancelReason;
    private Date date;
    private Long timeSlotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private Date createdAt;
    private Date updatedAt;
}
