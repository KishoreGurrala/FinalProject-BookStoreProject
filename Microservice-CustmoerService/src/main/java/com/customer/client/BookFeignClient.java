package com.customer.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.customer.dto.Book;

@FeignClient(name = "BookMicroservice", url="http://localhost:8282")
public interface BookFeignClient {
	@GetMapping("/book/getAllBooks")
	public List<Book> getBooks();
}	