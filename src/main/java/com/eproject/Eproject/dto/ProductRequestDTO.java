package com.eproject.Eproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data

public class ProductRequestDTO {
	
	@NotBlank
	private String name;
	
	private String description;
	
	
	@NotNull
	@Positive
	private Double price;
	
	@NotNull
	@PositiveOrZero
	private Integer stock;
	
	
	@NotNull
	private Long categoryId;
	

}
