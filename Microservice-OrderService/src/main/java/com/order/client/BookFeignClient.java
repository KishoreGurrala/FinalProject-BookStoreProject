package com.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.order.dto.Cart;



@FeignClient(name = "BookMicroservice", url="http://localhost:8282")
public interface BookFeignClient {
	
	@GetMapping("/book/cart/{bookId}/{quantity}")
	public String cartQuantity(@PathVariable("bookId")String bookid,@PathVariable("quantity")int quantity);
	@GetMapping("/book/quantityManager/{bookId}/{quantity}")
	public String quantityManager(@PathVariable("bookId")String bookid,@PathVariable("quantity")int quantity);
}