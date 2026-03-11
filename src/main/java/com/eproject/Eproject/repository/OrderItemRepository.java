package com.eproject.Eproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eproject.Eproject.entity.OrderItem;

public  interface  OrderItemRepository  extends JpaRepository<OrderItem,Long>{
	
	
	
	
	
	@Query("""
			
			SELECT COUNT(oi) > 0 FROM OrderItem oi
		    WHERE oi.order.user.id=:userId
		    AND  oi.product.id=:productId
		    AND oi.order.status=OrderStatus.PAID
		
			""")
	
	boolean hasUserPurchasedProduct(Long userId,Long productId);
	
	
	
	
	
	
	
	

}
