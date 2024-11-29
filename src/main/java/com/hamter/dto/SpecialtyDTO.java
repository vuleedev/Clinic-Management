package com.hamter.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SpecialtyDTO {
	private Long id;
    private String name;
    private String description;
    private String image;
    private Date createdAt;
    private Date updatedAt;
}
