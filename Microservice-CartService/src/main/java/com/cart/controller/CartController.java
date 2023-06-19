package com.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.client.BookFeignClient;
import com.cart.entity.Cart;
import com.cart.exceptions.payload.ApiResponse;
import com.cart.service.CartItemsService;
import com.cart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private BookFeignClient bookFeignClient;

	@Autowired
	private CartItemsService cartItemsService;

	// To get the cart Items of customer using customerId
	@GetMapping("/getCart")
	public ResponseEntity<?> getCustomer(@RequestHeader("userEmail") String email) {

		Cart cart = cartService.getCart(email);
		ApiResponse resp = ApiResponse.builder().status("Success")
				.message("These are the Cart Items Present In your Cart").result(cart).build();
		return new ResponseEntity<ApiResponse>(resp, HttpStatus.OK);
	}
	
	@GetMapping("/getCartItems")
	public Cart getCartItems(@RequestHeader("userEmail") String email) {

		Cart cart = cartService.getCart(email);
		return cart;
	}

	// To add the books into the cart
	@PutMapping("/addCart/{bookId}/{quantity}")
	public ResponseEntity<?> addToCart(@PathVariable("bookId") String bookId, @PathVariable("quantity") int quantity,
			@RequestHeader("userEmail") String email) {

		String msg = bookFeignClient.cart(bookId, quantity);

		if (msg.equalsIgnoreCase("Done")) {
			cartService.addToCart1(email, bookId, quantity);
			ApiResponse resp = ApiResponse.builder().status("Success").message("Cart Items added successfully.")
					.result(null).build();
			return new ResponseEntity<>(resp, HttpStatus.CREATED);
		}
		ApiResponse resp = ApiResponse.builder().status("Success").message(msg).result(null).build();
		return new ResponseEntity<>(resp, HttpStatus.CREATED);

	}

	// To delete book in cart using customer and book Id
	@GetMapping("/deleteBook/{bookId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("bookId") int bookId,
			@RequestHeader("userEmail") String email) {

		cartService.deleteBook(email, bookId);
		ApiResponse resp = ApiResponse.builder().status("Success").message("cart item deleted").result(null).build();
		return new ResponseEntity<ApiResponse>(resp, HttpStatus.OK);
	}

	// To remove the cart after placing the cart (or) customer removing cart using
	// customerId
	@GetMapping("removeCart")
	public ResponseEntity<ApiResponse> removeCart(@RequestHeader("userEmail") String email) {

		cartService.removeCart(email);
		ApiResponse resp = ApiResponse.builder().status("Success").message("Cart Deleted Successfully").result(null)
				.build();
		return new ResponseEntity<ApiResponse>(resp, HttpStatus.OK);
	}

}
