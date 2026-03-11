package com.eproject.Eproject.dto;



import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderHistoryResponseDTO {
	
	
	private Long orderId;
	
	private String status;
	
	
	private Double totalAmount;
	
	
	private LocalDateTime createdAt;
	


	

}
