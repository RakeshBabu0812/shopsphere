package com.eproject.Eproject.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailsResponseDTO {
	
	private Long orderId;
	
	private String status;
	
	private Double totalAmount;
	
	private LocalDateTime createdAt;
	
	
	private List<OrderItemResponseDTO> items;
	
	
}
