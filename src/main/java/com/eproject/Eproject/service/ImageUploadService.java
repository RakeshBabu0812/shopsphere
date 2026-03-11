package com.eproject.Eproject.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
	
	
	private final Cloudinary cloudinary;
	
	
	public Map uploadImage(MultipartFile file) {
		
		
		try {
			return cloudinary.uploader()
					.upload(file.getBytes(), 
							
							Map.of("folder","products")
							
							);
			
		}
		catch(Exception e) {
			throw new RuntimeException("Image upload failed");
		}
		
			
	}
	
	
	public void deleteImage(String publicId) {
		
		try {
			
			cloudinary.uploader().destroy(publicId, Map.of());
			
		}
		catch(Exception e) {
			
			throw new RuntimeException("Image deletion failed");
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	

}
