package com.eproject.Eproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eproject.Eproject.entity.Order;

public interface OrderRepository  extends JpaRepository<Order,Long>{
	
	
	
	
	
	Page<Order> findByUserId(Long userId,Pageable pageable);
	
	
	@Query("""
			SELECT COALESCE(SUM(o.totalAmount),0)
			FROM Order o WHERE o.status=OrderStatus.PAID

			""")
	Double getTotalRevenue();
	
	
	@Query("""
			SELECT COUNT(o) 
			From Order o
			WHERE o.status=OrderStatus.PAID
			"""
			)
	Long getTotalOrders();
	
	
	
	
    @Query("""    		
    		SELECT COUNT(o)
    		FROM Order o
    	   WHERE DATE(o.createdAt)=CURRENT_DATE
    	   AND o.status=OrderStatus.PAID
    				
		""")

	Long  getTodayOrders();
    
    
    
    
    @Query("""
    		SELECT DATE(o.createdAt),SUM(o.totalAmount)
    		FROM Order o
    		WHERE o.status=OrderStatus.PAID
    		GROUP BY DATE(o.createdAt)
    		ORDER BY DATE(o.createdAt)
    		
    		""")
    List<Object[]> getRevenueByDate();
    
    
    
    
    @Query("""
    		SELECT o
    		From Order o
    		WHERE o.status=OrderStatus.PAID
    		ORDER BY o.createdAt DESC  		
    		"""
    		)
    List<Order> getAllPaidOrders();
	
	 
	
	
	
	

}
