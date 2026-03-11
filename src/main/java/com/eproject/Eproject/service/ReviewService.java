package com.eproject.Eproject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eproject.Eproject.dto.ReviewRequestDTO;
import com.eproject.Eproject.dto.ReviewResponseDTO;
import com.eproject.Eproject.dto.ReviewSummaryDTO;
import com.eproject.Eproject.entity.Product;
import com.eproject.Eproject.entity.Review;
import com.eproject.Eproject.entity.User;
import com.eproject.Eproject.repository.OrderItemRepository;
import com.eproject.Eproject.repository.OrderRepository;
import com.eproject.Eproject.repository.ProductRepository;
import com.eproject.Eproject.repository.ReviewRepository;
import com.eproject.Eproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	
	
	private final ProductRepository productRepository;
	
	
	private final UserRepository userRepository;
	
	

	
	
	private final OrderItemRepository orderItemRepository;
	
	
	private final ReviewRepository reviewRepository;
	
	
	
	@Transactional
	public void addReview(Long userId,ReviewRequestDTO  dto) {
		
		Product product =productRepository.findById(dto.getProductId()).orElseThrow(()->new RuntimeException("Product not found"));
		
		
		User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
		
		
		boolean purchased=orderItemRepository.hasUserPurchasedProduct(userId,dto.getProductId());
		
		
		if(!purchased) {
			throw new RuntimeException("User has not purchased this product");
		}
		
		boolean alreadyReviewed=reviewRepository.existsByUserIdAndProductId(userId, dto.getProductId());
		
		if(alreadyReviewed) {
			
			throw new RuntimeException("User already reviewed this product");
			
		}
		
		Review review=Review.builder()
				.product(product)
				.user(user)
				.rating(dto.getRating())
				.comment(dto.getComment())
				.build();
		
		
		reviewRepository.save(review);
		
		updateProductRating(product);
		
				
	}
	
	public Page<ReviewResponseDTO> getReviews(Long productId,int page,int size){
		
		
		Pageable pageable=PageRequest.of(page,size,Sort.by("createdAt").descending());
		
		
		Page<Review> reviews=reviewRepository.findByProductId(productId,pageable);
		
		
		
		return reviews.map(review->ReviewResponseDTO.builder()
	                                                  .reviewId(review.getId())
	                                                  .userEmail(review.getUser().getEmail())
	                                                  .rating(review.getRating())
	                                                  .comment(review.getComment())
	                                                  .createdAt(review.getCreatedAt())
	                                                  .build()
	                                                  
			);
		
		
		
		
		
	
	}
	
	@Transactional
	public void updateReview(Long reviewId,Long userId,ReviewRequestDTO dto) {
		
		Review review=reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("Review not found"));
		
		
		if(!review.getUser().getId().equals(userId)) {
			
		throw new RuntimeException("User not authorized to update this review");
		}
		
		review.setRating(dto.getRating());
		review.setComment(dto.getComment());
		
		updateProductRating(review.getProduct());
	}
	
	
	@Transactional
	public void deleteReview(Long reviewId,Long userId) {
		
		Review review=reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("Review not found"));
		
		if(!review.getUser().getId().equals(userId)) {
			
			throw new RuntimeException("User not authorized to delete this review");
		
		}
		Product product=review.getProduct();

		reviewRepository.delete(review);
		
		updateProductRating(product);
	}
	
	public Double getAverageRating(Long productId) {
		
		
    Double avg=reviewRepository.getAverageRating(productId);
    
    
    return avg==null ? 0.0 : avg;
   		
	}
	
	public ReviewSummaryDTO getReviewSummary(Long productId) {
	
		
		Product product=productRepository.findById(productId).orElseThrow(()-> new RuntimeException("product not found"));
		
		Double avg=product.getAverageRating();
		
		Long count=product.getReviewCount();
		
		
		return ReviewSummaryDTO.builder()
				                .averageRating(avg)
				                .totalReviews(count).build();
		
	}
	
	
	
	
	private void updateProductRating(Product product) {
		
		Double avg=getAverageRating(product.getId());
		
		Long count=reviewRepository.countByProductId(product.getId());
		
		
		product.setAverageRating(avg);
		product.setReviewCount(count);
	
	}
	
	
	
	
	
	
	
	
	

}
