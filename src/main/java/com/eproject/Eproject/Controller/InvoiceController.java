package com.eproject.Eproject.Controller;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {
	
	
	@GetMapping("/{orderId}")
  public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long orderId){
	 	  
	  
		  try {
	  
		  String filePath="invoices/invoice_"+orderId+".pdf";
		  
		  File file=new File(filePath);
		  
		  if(!file.exists()) {
			  throw new RuntimeException("Invoice not found");
		  }
		  
		  FileInputStream fis=new FileInputStream(file);
		  
		  byte[] data=fis.readAllBytes();
		  
		  fis.close();
		  
		  HttpHeaders headers=new HttpHeaders();
		  
		  headers.setContentType(MediaType.APPLICATION_PDF);
		  
		  headers.setContentDispositionFormData("attachment",file.getName());
		  
		  
		  return ResponseEntity.ok().headers(headers).body(data);
		 
		  
		  
	  }
	 catch(Exception e) {
		  
		  throw new RuntimeException("Error downloading Invoice");
	 
	 
	 }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
  }
	
	
	
	
	

	
	
	
	
}
