package com.eproject.Eproject.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TopProductDTO {
	
	private Long productId;
	
	private String productName;
	
	private Long totalSold;
	
	

}
