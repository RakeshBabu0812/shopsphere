package com.eproject.Eproject.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eproject.Eproject.dto.ProductRequestDTO;
import com.eproject.Eproject.dto.ProductResponseDTO;
import com.eproject.Eproject.entity.Category;
import com.eproject.Eproject.entity.Product;
import com.eproject.Eproject.repository.CategoryRepository;
import com.eproject.Eproject.repository.ProductRepository;
import com.eproject.Eproject.specification.ProductSpecification;

import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor
public class ProductService {
	
	private  final CategoryRepository categoryRepository;
	
	
	private final  ProductRepository productRepository;
	
	
	private final ImageUploadService imageUploadService;
	
	
	
	@Transactional
	public ProductResponseDTO  createProduct(ProductRequestDTO request){
		
		
		Category category=categoryRepository.findById(request.getCategoryId())
				.orElseThrow(()->new RuntimeException("Category Not found"));
		
		Product product=Product.builder()
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.stock(request.getStock())
				.category(category)
				.build();
		
		
		Product saved=productRepository.save(product);
		
			
		return mapToDTO(saved);

	}
	
	
	public Page<ProductResponseDTO> getAllProducts(int page,int size){
	
		
		
		Pageable pageable=PageRequest.of(page, size,Sort.by("name").ascending());
		 
		
		return productRepository.findAll(pageable).map(this::mapToDTO);
		
	
		
		
	}
	
	
	@Transactional
	public void updateStock(Long productId,Integer newStock) {
		
		Product product=productRepository.findById(productId).orElseThrow(()->new RuntimeException("product not found"));
		
	
		
		if(newStock<0) {
			throw new RuntimeException("Invalid stock");
			
		}
		product.setStock(newStock);
		
		
	}
	
	@Transactional
	public void uploadProductImage(Long productId,MultipartFile file) {
		
		Product product=productRepository.findById(productId).orElseThrow(()->new RuntimeException());
		
		Map uploadResult=imageUploadService.uploadImage(file);
		
		
		String imageUrl=uploadResult.get("secure_url").toString();
		
		String publicId=uploadResult.get("public_id").toString();
		
		
		if(product.getImagePublicId()!=null) {
			imageUploadService.deleteImage(product.getImagePublicId());
			
		}
		
		product.setImageUrl(imageUrl);
		
		product.setImagePublicId(publicId);
		

		
		
		
		
	}
	
	public Page<ProductResponseDTO> searchProducts(
			
			String name,
			Double minPrice,
			Double maxPrice,
			Long categoryId,
			Double minRating,
			int page,
			int size
		){
		
		
		Specification<Product> spec=Specification
				                       .where(ProductSpecification.hasName(name))
				                       .and(ProductSpecification.hasMinPrice(minPrice))
				                       .and(ProductSpecification.hasMaxPrice(maxPrice))
				                       .and(ProductSpecification.hasCategory(categoryId))
				                       .and(ProductSpecification.hasMinRating(minRating));
		

	 Pageable pageable=PageRequest.of(page, size,Sort.by("createdAt").descending());
		  
		  
    return productRepository.findAll(spec, pageable).map(this::mapToDTO);
    

 
    
    
	
	}
	
	
	
	private ProductResponseDTO mapToDTO(Product product) {
		
		
		return ProductResponseDTO.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.stock(product.getStock())
				.categoryName(product.getCategory().getName())
				.averageRating(product.getAverageRating())
				.reviewCount(product.getReviewCount())
				.imageUrl(product.getImageUrl())
				.imagePublicId(product.getImagePublicId())
				.build();
			
	}
	
	
	
	
	

	
	
	
	
	

	
	
	
	

	
  

	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
