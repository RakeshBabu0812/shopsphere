package com.eproject.Eproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponseDTO {
	
	private Long productId;
	
	private String productName;
	
	private Double price;
	
	private Integer quantity;
	
	private Double total;

}
