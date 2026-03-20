package com.eproject.Eproject.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eproject.Eproject.entity.Order;
import com.eproject.Eproject.repository.OrderRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	
	private final OrderRepository orderRepository;
	
	
	public ByteArrayInputStream generateOrdersCSV() {
		
		List<Order> orders=orderRepository.getAllPaidOrders();
		
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		PrintWriter writer=new PrintWriter(out);
		
		
		writer.println("OrderId,UserEmail,Amount,Status,Date");
		
		for(Order order:orders) {
			
			writer.println(
					
					order.getId()+","+
					order.getUser().getEmail()+","+
					order.getTotalAmount()+","+
					order.getStatus()+","+
					order.getCreatedAt()
					);

		}
		writer.flush();
		
		
		return new ByteArrayInputStream(out.toByteArray());
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	

}
