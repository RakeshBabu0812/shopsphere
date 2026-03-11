package com.eproject.Eproject.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="payments",indexes= {@Index(name="idx_payment_order",columnList="order_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id",nullable=false,unique=true)
	private Order order;
	
	
	private String transactionId;
	
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	
	private Double amount;
	
	
	private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
	
	
	@PrePersist
	protected void createdAt() {
		
		this.createdAt=LocalDateTime.now();
	}
	
	@PreUpdate
	protected void updatedAt() {
		
		this.updatedAt=LocalDateTime.now();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
