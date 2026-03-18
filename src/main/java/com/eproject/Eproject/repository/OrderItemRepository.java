package com.eproject.Eproject.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
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
	
	
	
	@Query("""
			SELECT oi.product.id,oi.product.name,SUM(oi.quantity)
			FROM OrderItem oi
			WHERE oi.order.status=OrderStatus.PAID
			GROUP BY oi.product.id,oi.product.name
			ORDER BY SUM(oi.quantity) DESC
			
			
			""")
	List<Object[]> getTopSellingProducts(Pageable pageable);
	
	
	
	
	
	
	
	
	

}
