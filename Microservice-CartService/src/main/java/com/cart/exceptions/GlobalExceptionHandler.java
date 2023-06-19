package com.cart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cart.exceptions.BookNotFoundException;
import com.cart.exceptions.payload.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	/*
	 * @ExceptionHandler(Exception.class) public ResponseEntity<String>
	 * handleException(Exception e) { // Handle the exception here // You
	 * cancustomize the response based on the exception return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kishore"); }
	 */	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<ApiResponse> handleCustomException(CartNotFoundException e) {
		// Handle the custom exception here
		// You can customize the response based on the exception
		String msg = e.getMessage();
		ApiResponse resp = ApiResponse.builder().status("failed").message(msg).result(null).build();
		return new ResponseEntity<ApiResponse>(resp, HttpStatus.OK);
	}
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApiResponse> handleCustomException(BookNotFoundException e) {
		// Handle the custom exception here
		// You can customize the response based on the exception
		String msg = e.getMessage();
		ApiResponse resp = ApiResponse.builder().status("failed").message(msg).result(null).build();
		return new ResponseEntity<ApiResponse>(resp, HttpStatus.OK);
	}

}
