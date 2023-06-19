package com.cart.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.client.BookFeignClient;
import com.cart.dao.CartItemsRepository;
import com.cart.dao.CartRepository;
import com.cart.dto.Book;
import com.cart.entity.Cart;
import com.cart.entity.CartItems;
import com.cart.exceptions.BookNotFoundException;
import com.cart.exceptions.CartNotFoundException;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemsRepository cartItemsRepository;

	@Autowired
	private BookFeignClient bookFeignClient;

	// To get the cart Items
	public Cart getCart(String email) {

		Cart c = cartRepository.findById(email)
				.orElseThrow(() -> new CartNotFoundException("Your cart is empty"));
		return c;
	}

	// To add the book into the cart
	public void addToCart1(String email, String bookId, int quantity) {
		CartService cartService = new CartService();
		float totalPrice = 0f;
		Cart cart = new Cart();
		Book book = bookFeignClient.getBook(bookId);
		cart.setEmail(email);

		Optional<CartItems> items = cartItemsRepository.getByProperty(Integer.valueOf(book.getBookId()),email);
		CartItems cartItems=new CartItems();
		if (!items.isEmpty()) {
			cartItems = items.get();
		}
		if (cartItems.getQuantity() == null) {
			
			cartItems.setBookId(Integer.valueOf(book.getBookId()));
			cartItems.setBookPrice(book.getPrice());
			cartItems.setBookTitle(book.getTitle());
			cartItems.setQuantity(quantity);
			cartItems.setBookAuthor(book.getAuthor());

			try {
				Cart c = cartRepository.findById(cart.getEmail()).get();
				cartItems.setCart(c);
				c.getItems().add(cartItems);
				totalPrice += c.getPrice();
				totalPrice += (float) (cartItems.getBookPrice() * cartItems.getQuantity());
				c.setPrice(totalPrice);
				cartRepository.save(c);

			} catch (Exception e) {
				totalPrice += (float) (cartItems.getBookPrice() * cartItems.getQuantity());
				cartItems.setCart(cart);
				cart.getItems().add(cartItems);
				cart.setPrice(totalPrice);
				cartRepository.save(cart);
			}
		}
		else {
			cartItems.setQuantity(cartItems.getQuantity()+quantity);
			String msg = bookFeignClient.cart(bookId, cartItems.getQuantity());
			if(!msg.equalsIgnoreCase("Done")) {
				throw new BookNotFoundException(msg);
			}
			Cart c = cartRepository.findById(cart.getEmail()).get();
			cartItems.setCart(c);
			c.getItems().add(cartItems);
			totalPrice += c.getPrice();
			totalPrice += (float) (cartItems.getBookPrice() * quantity);
			c.setPrice(totalPrice);
			cartRepository.save(c);
		}
	}

	// To delete books from the cart
	public Cart deleteBook(String email, Integer bid) {
		float price = 0f;
		Cart c = cartRepository.findById(email)
				.orElseThrow(() -> new CartNotFoundException("Your cart is empty"));

		List<CartItems> items = c.getItems();
		Iterator<CartItems> iterator = items.iterator();
		while (iterator.hasNext()) {
			CartItems item = iterator.next();

			if (item.getBookId() == (bid)) {

				price += (float) (item.getBookPrice() * item.getQuantity());
				iterator.remove();
			}
		}
		float totalPrice = c.getPrice() - price;
		c.setPrice(totalPrice);

		cartRepository.save(c);
		return c;
	}

	// To remove whole cart
	public String removeCart(String email) {

		try {
			cartRepository.deleteById(email);
		} catch (Exception e) {

			throw new CartNotFoundException("Your cart is empty");
		}

		return "done";

	}
}
