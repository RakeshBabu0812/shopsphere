package com.eproject.Eproject.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eproject.Eproject.dto.RevenueByDateDTO;
import com.eproject.Eproject.dto.TopProductDTO;
import com.eproject.Eproject.service.AdminAnalyticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/analytics")
@RequiredArgsConstructor
public class AdminAnalyticsController {
	
	private final AdminAnalyticsService analyticsService;
	
	
	
	@GetMapping("/revenue")
	public Double getTotalRevenue() {
		
	   return analyticsService.getTotalRevenue();	
	}
	
	@GetMapping("/orders")
	public Long getTotalOrders() {
		
		return analyticsService.getTotalOrders();
		
		
	}
	
	@GetMapping("/orders/today")
	public Long getTodayOrders() {
		
		return analyticsService.getTodayOrders();

	}
	
	@GetMapping("/top-products")
	public List<TopProductDTO> getTopProducts(){
		
		return analyticsService.getTopProducts();
		
	}
	
	@GetMapping("/revenue-by-date")
	public List<RevenueByDateDTO> getRevenueByDate(){
		
		return analyticsService.getRevenueByDate();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
