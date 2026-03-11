package com.eproject.Eproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eproject.Eproject.entity.Order;

public interface OrderRepository  extends JpaRepository<Order,Long>{
	
	
	Page<Order> findByUserId(Long userId,Pageable pageable);
	
	
	
	
	

}
