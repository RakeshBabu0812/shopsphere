package com.eproject.Eproject.Controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eproject.Eproject.dto.PagedResponse;
import com.eproject.Eproject.dto.ProductRequestDTO;
import com.eproject.Eproject.dto.ProductResponseDTO;
import com.eproject.Eproject.entity.Product;
import com.eproject.Eproject.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
	
	
	private final ProductService productService;
	
	
	@PostMapping
	public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO request){
		
		
	   return ResponseEntity.ok(productService.createProduct(request));
	  
	}
	
	@GetMapping
	public ResponseEntity<?> getProducts(@RequestParam(defaultValue="0") int page,
			                                                     @RequestParam(defaultValue="5") int size){
		
		
		
	 Page<ProductResponseDTO> productPage=productService.getAllProducts(page, size);
	 
	 
	 return ResponseEntity.ok(
			  
			 new PagedResponse<>(
			 productPage.getContent(),
			 
			 productPage.getNumber(),
			 
			 productPage.getSize(),
			 
			 productPage.getTotalElements(),
			 
			 productPage.getTotalPages()
			
			 )
			 );
	 
		
	}
	
	@PutMapping("/{id}/stock")
	public ResponseEntity<String> updateStock(@PathVariable Long id,@RequestParam Integer stock){
		
		
		    productService.updateStock(id, stock);
		    
		    return ResponseEntity.ok("Stock Updated Successsfully");
		  
		    
		
	}
	
	@PostMapping("{productId}/image")
	public ResponseEntity<String> uploadProductImage(@PathVariable 	Long productId,@RequestParam("file") MultipartFile file){
		
		
		
		productService.uploadProductImage(productId, file);
	
		return ResponseEntity.ok("Image uploaded successfully");
		
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<ProductResponseDTO>> searchProducts(
			
			@RequestParam(required=false) String name,
			@RequestParam(required=false) Double minPrice,
			@RequestParam(required=false)  Double maxPrice,
			@RequestParam(required=false)  Long categoryId,
			@RequestParam(required=false) Double minRating,
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="5") int size
			){
		
		
		
		return ResponseEntity.ok(
				
				productService.searchProducts(name, minPrice, maxPrice, categoryId, minRating, page, size)
				
				);
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
