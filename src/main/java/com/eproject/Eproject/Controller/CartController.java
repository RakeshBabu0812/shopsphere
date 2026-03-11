package com.eproject.Eproject.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eproject.Eproject.dto.CartResponseDTO;
import com.eproject.Eproject.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	
	@PostMapping("/add")
	public ResponseEntity<String> addToCart(@RequestParam Long userId,@RequestParam Long productId,@RequestParam Integer quantity){
		
	
		  cartService.addToCart(userId, productId, quantity);
		  
		  return ResponseEntity.ok("Product added to cart");
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<CartResponseDTO> getCart(@PathVariable Long userId){
		
		
		return ResponseEntity.ok(cartService.getCart(userId));
	
	}
	
	
	
	

	

}
