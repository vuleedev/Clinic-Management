package com.hamter.dto;

import java.util.Date;

import lombok.Data;

@Data
public class HistoryDTO {

	private Long id;
    private Long userId;
    private String userName;
    private Long doctorId;
    private String doctorName;
    private String description;
    private String files;
    private Date createdAt;
    private Date updatedAt;
}
