package com.eproject.Eproject.Controller;

import java.io.ByteArrayInputStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eproject.Eproject.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
public class ReportController {
	
	private final ReportService reportService;
	
	
	
	
	
	@GetMapping("/orders")
	public ResponseEntity<byte[]> downloadOrdersCSV(){
		
		ByteArrayInputStream  stream=reportService.generateOrdersCSV();
		
		
		return ResponseEntity.ok()
				           .header("Content-Disposition", "attachment; filename=orders.csv")
				           .header("Content-Type","text/csv")
				           .body(stream.readAllBytes());
		
		
		
		
	}
	
	
	
	
	
	
	

	
	
}
