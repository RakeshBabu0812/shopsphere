package com.eproject.Eproject.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eproject.Eproject.dto.ReviewRequestDTO;
import com.eproject.Eproject.dto.ReviewResponseDTO;
import com.eproject.Eproject.dto.ReviewSummaryDTO;
import com.eproject.Eproject.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor

public class ReviewController {
	
	private final ReviewService reviewService;
	
	@PostMapping("/user/{userId}")
	
	public ResponseEntity<String>addReview(@PathVariable Long userId,@RequestBody ReviewRequestDTO dto){
		
		
		reviewService.addReview(userId, dto);
		
		
		return ResponseEntity.ok("Review added successfully");
		
		
	}

	@GetMapping("/product/{productId}")
	
	public ResponseEntity<Page<ReviewResponseDTO>> getReviews(@PathVariable Long productId,
			                                           @RequestParam(defaultValue="0") int page,
			                                           
			                                           @RequestParam(defaultValue="5") int size ){
		
			                                          
			   
		return ResponseEntity.ok(reviewService.getReviews(productId, page, size));
		
			
	}
	
	@PutMapping("/{reviewId}/user/{userId}")
	public ResponseEntity<String> updateReview(@PathVariable Long reviewId,@PathVariable Long userId,@RequestBody ReviewRequestDTO dto){
		
		
               reviewService.updateReview(reviewId, userId, dto);
               
               
             return  ResponseEntity.ok("Updated successfully");
	
	
	}
	
	@DeleteMapping("/{reviewId}/user/{userId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId,@PathVariable Long userId){
	
		reviewService.deleteReview(reviewId, userId);
		
		
		return ResponseEntity.ok("Deleted succesfully");
		
		
		
		
	}
	
	@GetMapping("/product/{productId}/summary")
	public ResponseEntity<ReviewSummaryDTO> getReviewSummary(@PathVariable Long productId){
		
		return ResponseEntity.ok(reviewService.getReviewSummary(productId));
	
		
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
