package com.eproject.Eproject.service;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.eproject.Eproject.entity.Order;
import com.eproject.Eproject.entity.OrderItem;
import com.eproject.Eproject.entity.OrderStatus;
import com.eproject.Eproject.entity.Payment;
import com.eproject.Eproject.entity.Product;
import com.eproject.Eproject.entity.PaymentStatus;
import com.eproject.Eproject.repository.OrderRepository;
import com.eproject.Eproject.repository.ProductRepository;
import com.eproject.Eproject.repository.PaymentRepository;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	
	
	private final PaymentRepository paymentRepository;
	
	private final OrderRepository orderRepository;
	
	
	private final ProductRepository productRepository;
	
	
	private final InvoiceService invoiceService;
	
	
	
	
	
	
	
	@Transactional
	public void markPaymentSuccess(Long orderId,String transactionId) {
		
	//	System.out.println("payment is succeded");
		

	Payment payment=paymentRepository.findByOrderId(orderId)
			                    .orElseThrow(()->new RuntimeException("Payment not found"));
	
	System.out.println(payment.getStatus()+"this in mark payment success");
	
	
	 if (payment.getStatus() != PaymentStatus.PENDING) {
	        return;
	    
	}

	
	payment.setStatus(PaymentStatus.SUCCESS);
	
	payment.setTransactionId(transactionId);
	
	Order order=payment.getOrder();
	
	order.setStatus(OrderStatus.PAID);
	
	
	invoiceService.generateInvoice(order);
	
	
	System.out.println("payment is succeded");
	

		
	}
	
	@Transactional
	public void markPaymentFailed(Long orderId) {
		
		
		Payment payment=paymentRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException("Payment not found"));
		
		
	
		if(payment.getStatus()!=PaymentStatus.PENDING) {
			return;
		}
		
		 restoreStock(payment.getOrder());
	
		payment.setStatus(PaymentStatus.FAILED);
		
		Order order=payment.getOrder();
		
		order.setStatus(OrderStatus.CANCELLED);
		
		
		
	}
	
	
	@Transactional
	public void markPaymentExpired(Long orderId) {
		
			Payment payment=paymentRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException("Payment not found"));
			
			   if (payment.getStatus() != PaymentStatus.PENDING) {
			        return;
			    }
			
			   
			
			restoreStock(payment.getOrder());
			
			payment.setStatus(PaymentStatus.EXPIRED);
			
			
			Order order=payment.getOrder();
			
			order.setStatus(OrderStatus.CANCELLED);
		
	}
	
	 private void restoreStock(Order order) {
		 
		 
		 for(OrderItem orderItem:order.getItems()) {
			 Product product=productRepository.findById(orderItem.getProduct().getId())
					 .orElseThrow(() -> new RuntimeException("Product not found"));
			
			 product.setStock(product.getStock()+orderItem.getQuantity());
			
		 }
		 
		
		 
	 }
	
	
	@Transactional
	public String createStripeSession(Long orderId) throws Exception{
		
	

		Order order=orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));
		 
		
		Payment payment=paymentRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException("Payment not found"));
		
		
		
		if(order.getStatus()==OrderStatus.PAID || payment.getStatus()==PaymentStatus.SUCCESS) {
			
			throw new RuntimeException("Order Already Paid");
			
		}
		
		
		if(payment.getStatus()==PaymentStatus.FAILED || payment.getStatus()==PaymentStatus.EXPIRED) {
			
			System.out.println("this is in Payment Expired or failed ");
			
			
			
			for(OrderItem item:order.getItems()) {
				
				Product product=productRepository.findById(item.getProduct().getId())
						.orElseThrow(()->new RuntimeException("Product not found"));
				
				if(product.getStock()<item.getQuantity()) {
					
					throw new RuntimeException("Product out of stock.Cannot retry Payment");
					
				}
				product.setStock(product.getStock()-item.getQuantity());
				

			}
				

              payment.setStatus(PaymentStatus.PENDING);
              order.setStatus(OrderStatus.PENDING);
              
    
		}
		
		
		
		SessionCreateParams.LineItem.PriceData.ProductData productData=SessionCreateParams.LineItem.PriceData.ProductData.builder()
				                                                             .setName("Order #"+orderId).build();
		
		
		SessionCreateParams.LineItem.PriceData priceData=SessionCreateParams.LineItem.PriceData.builder()
				                                             .setCurrency("inr")
				                                             .setUnitAmount((long)(order.getTotalAmount()*100))
				                                             .setProductData(productData)
				                                             .build();
		
		SessionCreateParams.LineItem lineItem=SessionCreateParams.LineItem.builder()
				                                 .setQuantity(1L)
				                                 .setPriceData(priceData)
				                                 .build();
		
	SessionCreateParams params=SessionCreateParams.builder()
			                           .setMode(SessionCreateParams.Mode.PAYMENT)
			                           .setSuccessUrl("http://localhost:3000/payment-success")
			                           .setCancelUrl("http://localhost:3000/payment-cancel")
			                           .addLineItem(lineItem)
			                           .putMetadata("orderId", orderId.toString())
			                           .build();

	
	
	Session session=Session.create(params);
	
			                           
		
		
		return session.getUrl();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
