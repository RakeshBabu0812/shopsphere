package com.eproject.Eproject.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevenueByDateDTO {

	private LocalDate date;
	
	private Double revenue;
	
	
	
	
}
