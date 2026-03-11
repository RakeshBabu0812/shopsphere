package com.eproject.Eproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewSummaryDTO {
	
	
	private Double averageRating;
	
	private Long totalReviews;
	
	
}
