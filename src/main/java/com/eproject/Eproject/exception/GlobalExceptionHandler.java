package com.eproject.Eproject.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<String> handleOptimisticLocking(){
		
		return ResponseEntity.badRequest().body("Stock was Modified by another User.please try again");
		
	}
	
	

	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException ex){
		
		return ResponseEntity.badRequest().body(ex.getMessage());
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
