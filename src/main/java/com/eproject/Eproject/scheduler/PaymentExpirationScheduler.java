package com.eproject.Eproject.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eproject.Eproject.entity.Payment;
import com.eproject.Eproject.entity.PaymentStatus;
import com.eproject.Eproject.repository.PaymentRepository;
import com.eproject.Eproject.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Component

@RequiredArgsConstructor
public class PaymentExpirationScheduler {
	
	
	private final PaymentRepository paymentRepository;
	
	
	private final PaymentService paymentService;
	
	
	@Scheduled(fixedRate=60000)

	public void expirePendingPayments() {
		
		LocalDateTime cutOffTime=LocalDateTime.now().minusMinutes(30);
		
		
		List<Payment> pendingPayments=paymentRepository.findExpiredPayments(PaymentStatus.PENDING, cutOffTime);
		
		
		
		for(Payment payment:pendingPayments) {
			
			paymentService.markPaymentExpired(payment.getOrder().getId());
		
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
