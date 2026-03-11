package com.eproject.Eproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="products",indexes= {
		@Index(name="idx_product_name",columnList="name"),
		@Index(name="idx_product_category",columnList="category_id")
		
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(nullable=false)
	private String name;
	
	
	@Column(length=2000)
	private String description;
	
	@Column(nullable=false)
	private Double price;
	
	@Column(nullable=false)
	private Integer stock;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id",nullable=false)
	private Category category;
	
	@Column(nullable=false)
	@Builder.Default
	private Double averageRating=0.0;
	
	@Column(nullable=false)
	@Builder.Default
	private Long reviewCount=0L;
	
	
	@Column
	private String imageUrl;
	
	@Column
	private String imagePublicId;

	
	
	@Version
	private Long version;
	
	
	
  
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	
	@PrePersist
	protected void onCreate() {
		this.createdAt=LocalDateTime.now();
	}
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt=LocalDateTime.now();

	}
	

	
	
	

}



