package com.hamter.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BookingDTO {

	private Long id;
    private String statusId;
    private Long doctorId;
    private Long userId;
    private String cancelReason;
    private Date date;
    private Long timeSlotId;
    private Date createdAt;
    private Date updatedAt;
}
