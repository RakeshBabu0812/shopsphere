package com.eproject.Eproject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eproject.Eproject.dto.OrderDetailsResponseDTO;
import com.eproject.Eproject.dto.OrderHistoryResponseDTO;
import com.eproject.Eproject.dto.OrderItemResponseDTO;
import com.eproject.Eproject.entity.Cart;
import com.eproject.Eproject.entity.Order;
import com.eproject.Eproject.entity.OrderItem;
import com.eproject.Eproject.entity.OrderStatus;
import com.eproject.Eproject.entity.Payment;
import com.eproject.Eproject.entity.PaymentStatus;
import com.eproject.Eproject.entity.Product;
import com.eproject.Eproject.entity.User;
import com.eproject.Eproject.repository.CartRepository;
import com.eproject.Eproject.repository.OrderRepository;
import com.eproject.Eproject.repository.PaymentRepository;
import com.eproject.Eproject.repository.ProductRepository;

import jakarta.persistence.OptimisticLockException;

import com.eproject.Eproject.entity.CartItem;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class OrderService {
	
	
	
	
	private final CartRepository cartRepository;
	
	
	private final OrderRepository orderRepository;
	
	
	
	private final ProductRepository productRepository;
	
	
	
	private final PaymentRepository paymentRepository;
	
	


	
	
	
	@Transactional
	public Long checkOut(Long userId) {
		
	
		
		Cart cart=cartRepository.findByUserId(userId).orElseThrow(()->new RuntimeException("Cart not found"));
		
		
		if(cart.getItems().isEmpty()) {
			throw new RuntimeException("Cart is Empty");
		}
		
		User user=cart.getUser();
		
		Order order=Order.builder().user(user).status(OrderStatus.PENDING).build();
		
		
		double totalAmount=0;
		
		try {

		for(CartItem cartItem:cart.getItems()) {
			
			Product product=productRepository.findById(cartItem.getProduct().getId())
					.orElseThrow(()->new RuntimeException("Product not found"));
			
			int quantity=cartItem.getQuantity();
			
			if(product.getStock()<quantity) {
				throw new RuntimeException("Insufficient Stock for product: "+product.getName());
			}
			
			product.setStock(product.getStock()-quantity);
			
			
			
			double price=product.getPrice();
			
			totalAmount=totalAmount+(price*quantity);
			
			
			OrderItem orderItem=OrderItem.builder()
					.order(order)
					.product(product)
					.quantity(quantity)
					.priceAtPurchase(price).build();
			
			
			order.getItems().add(orderItem);
			
		     
		}

		}
		catch(ObjectOptimisticLockingFailureException ex) {
			
			throw new RuntimeException("Stock updated by another user. Please retry.");
			
		}
		
		order.setTotalAmount(totalAmount);
		
		Order savedOrder=orderRepository.save(order);
		
		Payment payment=Payment.builder()
				.order(savedOrder)
				.status(PaymentStatus.PENDING)
				.amount(totalAmount)
				.build();
		
		
		paymentRepository.save(payment);
		
	
		
		cart.getItems().clear();
		
		
		return savedOrder.getId();		 
	}
	
	
	public Page<OrderHistoryResponseDTO> getUserOrders(Long userId,int page,int size){
		
		Pageable pageable=PageRequest.of(page, size);
		
		
		Page<Order> orders=orderRepository.findByUserId(userId, pageable);
		
		
		
		return orders.map(order->OrderHistoryResponseDTO.builder() 
				                     .orderId(order.getId())
				                     .status(order.getStatus().name())
				                     .totalAmount(order.getTotalAmount())
				                     .createdAt(order.getCreatedAt())
				                     .build()
				);
	}
	
	public OrderDetailsResponseDTO  getOrderDetails(Long orderId) {
		
		Order order=orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));

		
		List<OrderItemResponseDTO> itemDTOs=order.getItems()
				                             .stream()
				                             .map(item->OrderItemResponseDTO.builder()
				                            		      .productId(item.getProduct().getId())
				                            		      .productName(item.getProduct().getName())
				                            		      .quantity(item.getQuantity())
				                            		      .price(item.getPriceAtPurchase())
				                            		      .build()
				                            		    ).toList();
		
		
		return OrderDetailsResponseDTO.builder()
				                        .orderId(order.getId())
				                        .status(order.getStatus().name())
				                        .totalAmount(order.getTotalAmount())
				                        .createdAt(order.getCreatedAt())
				                        .items(itemDTOs).build();
				
	}
	
	
}
