package com.eproject.Eproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eproject.Eproject.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Long>{
	
	Page<Review> findByProductId(Long productId,Pageable pageable);
	
	boolean existsByUserIdAndProductId(Long userId,Long productId);
	
	
	
	@Query("""
			SELECT AVG(r.rating) 
			FROM Review r
		    WHERE r.product.id=:productId
			
			""")	
	Double getAverageRating(Long productId);
	
	Long countByProductId(Long productId);
	
	


}
