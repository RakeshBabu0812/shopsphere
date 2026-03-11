package com.eproject.Eproject.Controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eproject.Eproject.dto.OrderDetailsResponseDTO;
import com.eproject.Eproject.dto.OrderHistoryResponseDTO;
import com.eproject.Eproject.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	
	@PostMapping("/checkout/{userId}")
	public ResponseEntity<Long> checkOut(@PathVariable Long userId){
		
		
		
		Long orderId=orderService.checkOut(userId);
		
		return ResponseEntity.ok(orderId);

	}
	
	
	@GetMapping("user/{userId}")
	public  ResponseEntity<Page<OrderHistoryResponseDTO>> getUserOrder(@PathVariable Long userId,
			                                          @RequestParam(defaultValue="0") int page ,
			                                          @RequestParam(defaultValue="5") int size){
		
	
		return ResponseEntity.ok(orderService.getUserOrders(userId, page, size));
	
		
		
		
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDetailsResponseDTO> getOrderDetails(@PathVariable Long orderId){
		
         
		return ResponseEntity.ok(orderService.getOrderDetails(orderId));
		
	}
	
	
	
	
	
	
	

}
