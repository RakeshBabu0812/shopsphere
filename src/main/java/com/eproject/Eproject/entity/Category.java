package com.eproject.Eproject.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="categories",uniqueConstraints=@UniqueConstraint(columnNames="name"))

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {


	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	 
	 @Column(nullable=false)
	private String name;
	
	
	private String description;
	
	
	@OneToMany(mappedBy="category",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Product> products;
	
	
	

	
	
}
