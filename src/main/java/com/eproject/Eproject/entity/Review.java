package com.eproject.Eproject.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private int rating;
	
	@Column(length=1000)
	private String comment;  
	
	
	private LocalDateTime createdAt;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@PrePersist
	public void prePersist() {
		
		this.createdAt=LocalDateTime.now();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
