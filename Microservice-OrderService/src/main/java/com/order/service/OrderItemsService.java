package com.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.dao.OrderItemsRepository;
import com.order.dto.CartItems;
import com.order.entity.OrderItems;

@Service
public class OrderItemsService {

	@Autowired
	private OrderItemsRepository orderItemsRepository;

	public void addOrderItems(String email, List<CartItems> items) {

		for (CartItems item : items) {

			OrderItems o = new OrderItems();

			o.setEmail(email);
			o.setBookId(item.getBookId());
			o.setBookPrice(item.getBookPrice());
			o.setBookTitle(item.getBookTitle());
			o.setItemId(item.getItemId());
			o.setQuantity(item.getQuantity());

			orderItemsRepository.save(o);

		}
	}
}
