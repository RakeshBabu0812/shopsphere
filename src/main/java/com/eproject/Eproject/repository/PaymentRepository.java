package com.eproject.Eproject.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eproject.Eproject.entity.Payment;
import com.eproject.Eproject.entity.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
	
	
	Optional<Payment> findByOrderId(Long orderId);
	
	
//	List<Payment> findByStatusAndCreatedAtBefore(PaymentStatus status,LocalDateTime time);
	
	
	
@Query("""  
		SELECT p FROM Payment p 
	    WHERE p.status=:status AND 
	    COALESCE(p.updatedAt,p.createdAt) < :cutoffTime 
	   
	    """)
List<Payment> findExpiredPayments(PaymentStatus status,LocalDateTime cutoffTime);
	



}
