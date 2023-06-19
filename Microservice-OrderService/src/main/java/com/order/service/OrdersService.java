package com.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.dao.OrderRepository;
import com.order.dto.CartItems;
import com.order.entity.OrderItems;
import com.order.entity.Orders;

@Service
public class OrdersService {

	@Autowired
	private OrderRepository orderRepository;

	public List<Orders> getOrders() {
		List<Orders> list = orderRepository.findAll();
		return list;

	}

	public void addOrder(String email, float price, List<CartItems> items) {

		Orders orders = new Orders();

		orders.setEmail(email);
		orders.setTotalPrice(price);
		orders.setOrderDate(new Date());
		
		for (CartItems item : items) {

			OrderItems o = new OrderItems();

			o.setEmail(email);
			o.setBookId(item.getBookId());
			o.setBookPrice(item.getBookPrice());
			o.setBookTitle(item.getBookTitle());
			o.setItemId(item.getItemId());
			o.setQuantity(item.getQuantity());
			o.setOrders(orders);
			orders.getItems().add(o);

		}
		orderRepository.save(orders);

	}
	
	public List<Orders> getOrder(String email) {
		List<Orders> list = orderRepository.findAllWithCondition(email);
		return list;

	}
}
