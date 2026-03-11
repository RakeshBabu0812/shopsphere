package com.eproject.Eproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eproject.Eproject.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
	
	Optional<Cart> findByUserId(Long userId);
	
	
	

}
