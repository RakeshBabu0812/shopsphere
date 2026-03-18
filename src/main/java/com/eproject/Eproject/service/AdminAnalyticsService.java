package com.eproject.Eproject.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.eproject.Eproject.dto.RevenueByDateDTO;
import com.eproject.Eproject.dto.TopProductDTO;
import com.eproject.Eproject.repository.OrderItemRepository;
import com.eproject.Eproject.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAnalyticsService {
	
	private final OrderRepository orderRepository;
	
	private final OrderItemRepository orderItemRepository;
	
	
	public Double getTotalRevenue() {
		
		return orderRepository.getTotalRevenue();
		
	}
	
	public Long getTotalOrders() {
		System.out.println(orderRepository.getTotalOrders());
		
		return orderRepository.getTotalOrders();
		
	}
	public Long getTodayOrders() {
		
		return orderRepository.getTodayOrders();
		
	}
	
	public List<TopProductDTO>  getTopProducts(){
		
		
		List<Object[]> results=orderItemRepository.getTopSellingProducts(PageRequest.of(0, 5));
		
		
		return results.stream()
				       .map(r->TopProductDTO.builder()
				    		       .productId((Long)r[0])
				    		       .productName((String)r[1])
				    		       .totalSold((Long)r[2])
				    		       .build()
				    	
				    		   ).toList();
		
	}
	
	public List<RevenueByDateDTO> getRevenueByDate(){
		
	List<Object[]> results=orderRepository.getRevenueByDate();
	
	
	return results.stream()
			           .map(r->RevenueByDateDTO.builder()
			        		            .date(((java.sql.Date)r[0]).toLocalDate())
			        		            .revenue(((Number)r[1]).doubleValue())
			        		            .build()			        		   
			        		   ).toList();

	
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
