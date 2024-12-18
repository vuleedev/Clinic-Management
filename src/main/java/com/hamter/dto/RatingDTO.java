package com.hamter.dto;

import lombok.Data;

@Data
public class RatingDTO {

	private Long historyId;
    private Integer ratingValue;
    private String comment;
}
