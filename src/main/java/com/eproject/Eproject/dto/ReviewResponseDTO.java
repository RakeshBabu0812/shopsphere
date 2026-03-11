package com.eproject.Eproject.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDTO {
	
	
	private Long reviewId;
	
	private String userEmail;
	
	private int rating;
	
	private String comment;
	
	
	private LocalDateTime createdAt;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
