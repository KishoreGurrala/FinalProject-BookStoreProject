package com.cart.dto;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	
	private String bookId;
	
	private String title;
	
	private String author;
	
	private float price;
	
	private int quantity;
	
	

}
