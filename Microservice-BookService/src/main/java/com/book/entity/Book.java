package com.book.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	
	@Id
	private String bookId;
	
	private String title;
	
	private String author;
	
	private float price;
	
	private int quantity;
	
	

}
