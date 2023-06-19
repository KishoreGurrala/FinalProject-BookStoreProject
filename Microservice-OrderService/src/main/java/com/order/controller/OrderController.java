package com.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.client.BookFeignClient;
import com.order.client.CartFeignClient;
import com.order.dto.Cart;
import com.order.dto.CartItems;
import com.order.entity.Orders;
import com.order.exceptions.ApiResponse;
import com.order.exceptions.CartNotFoundException;
import com.order.service.OrderItemsService;
import com.order.service.OrdersService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartFeignClient cartFeignClient;

	@Autowired
	private BookFeignClient bookFeignClient;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrderItemsService orderItemsService;

	@GetMapping("/getOrders")
	public ResponseEntity<ApiResponse> getData() {
		List<Orders> list = ordersService.getOrders();
		ApiResponse resp = ApiResponse.builder().status("Success").message("These are the All orders.").result(list)
				.build();
		return new ResponseEntity<ApiResponse>(resp, HttpStatus.OK);
	}

	@GetMapping("/getOrder")
	public ResponseEntity<ApiResponse> getOrder(@RequestHeader("userEmail") String email) {
		List<Orders> list = ordersService.getOrder(email);
		ApiResponse resp = ApiResponse.builder().status("Success").message("These are the Orders Placed by you")
				.result(list).build();
		return new ResponseEntity<ApiResponse>(resp, HttpStatus.OK);
	}

	@GetMapping("/completeOrder")
	public ResponseEntity<ApiResponse> makeOrder(@RequestHeader("userEmail") String email) {

		Cart cart = cartFeignClient.getCart(email);
		System.out.println(cart);

		if (cart.getEmail() == null) {
			throw new CartNotFoundException("Your cart is Empty");
		}

		List<CartItems> items = cart.getItems();
		for (CartItems item : items) {
			String msg = bookFeignClient.cartQuantity(item.getBookId() + "", item.getQuantity());

			if (msg.equalsIgnoreCase("Done")) {
				continue;
			} else {
				throw new CartNotFoundException(msg);
			}
		}
		for (CartItems item : items) {
			bookFeignClient.quantityManager(item.getBookId() + "", item.getQuantity());
		}
		ordersService.addOrder(email, cart.getPrice(), cart.getItems());
		cartFeignClient.removeCart(email);

		ApiResponse resp = ApiResponse.builder().status("Success").message("Order placed Successfully").result(null)
				.build();
		return new ResponseEntity<ApiResponse>(resp, HttpStatus.OK);
	}

}
