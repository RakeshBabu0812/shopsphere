package com.eproject.Eproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eproject.Eproject.dto.CartItemResponseDTO;
import com.eproject.Eproject.dto.CartResponseDTO;
import com.eproject.Eproject.entity.Cart;
import com.eproject.Eproject.entity.CartItem;
import com.eproject.Eproject.entity.Product;
import com.eproject.Eproject.entity.User;
import com.eproject.Eproject.repository.CartItemRepository;
import com.eproject.Eproject.repository.CartRepository;
import com.eproject.Eproject.repository.ProductRepository;
import com.eproject.Eproject.repository.UserRepository;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	
	
	private final CartRepository cartRepository;
	
	private final CartItemRepository cartItemRepository;
	
	private final UserRepository userRepository;
	
	
	private final ProductRepository productRepository;
	
	
	@Transactional
	public void addToCart(Long userId,Long productId,Integer quantity) {
		
		
		User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
		
		
		Product product=productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
		
		if(product.getStock()<quantity) {
			throw new RuntimeException("Insufficient stock");
		}
		
		Cart cart=cartRepository.findByUserId(userId).orElseGet(()->{
			
			
			Cart newCart=Cart.builder().user(user).build();
			
	
			return cartRepository.save(newCart);
		
		});
		
		CartItem cartItem=cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElse(null);
		
	
		if(cartItem!=null) {
			
			cartItem.setQuantity(cartItem.getQuantity()+quantity);
			
		}
		else {
			
	       CartItem newItem=CartItem.builder().cart(cart).product(product).quantity(quantity).build();
	       
	       cart.getItems().add(newItem);
	  	       
		}
		
	
	}

	@Transactional(readOnly=true)
	public CartResponseDTO  getCart(Long userId) {
		
		
		Cart cart=cartRepository.findByUserId(userId).orElseThrow(()->new RuntimeException("Cart not found"));
		
		
		
		
		List<CartItemResponseDTO> itemsDTO=cart.getItems().stream().map(item->{
		   
	                  	Product product=item.getProduct();
	                  	
	                  	double total=product.getPrice()*item.getQuantity();
	                  	   
	                  	
	                  	return CartItemResponseDTO.builder()
	                  			.productId(product.getId())
	                  			.productName(product.getName())
	                  			.price(product.getPrice())
	                  			.quantity(item.getQuantity())
	                  			.total(total)
	                  			.build();
	                  	
	                }).toList();
		
		
		
		double cartTotal=itemsDTO.stream().mapToDouble(CartItemResponseDTO::getTotal).sum();
		
		
		return CartResponseDTO.builder()
				.userId(userId)
				.items(itemsDTO)
				.cartTotal(cartTotal)
				.build();
		
		
	}
	
	
	
	
	

}
