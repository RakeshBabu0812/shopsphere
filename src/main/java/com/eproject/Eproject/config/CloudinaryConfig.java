package com.eproject.Eproject.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;



@Configuration
public class CloudinaryConfig {
	
	
	@Value("${cloudinary.cloud-name}")
	public String cloudName;
	
	
	@Value("${cloudinary.api-key}")
	public String apiKey;
	
	
	@Value("${cloudinary.api-secret}")
	public String apiSecret;
	
	
	@Bean
	public Cloudinary cloudinary() {
		
		Map<String,String> config=new HashMap<>();
		
		
		config.put("cloud_name", cloudName);
		
		config.put("api_key", apiKey);
		
		config.put("api_secret", apiSecret);
		
		
		return new Cloudinary(config);
		
	}
	

}
