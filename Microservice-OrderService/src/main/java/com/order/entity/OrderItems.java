package com.order.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
	
	
	private String email;
	
	@Id
	private int itemId;
	private String bookTitle;
	private float bookPrice;
	private Integer quantity;
	private int bookId;
	
	@ManyToOne
	@JoinColumn(name = "emails")
	@JsonBackReference
	private Orders orders;
}
