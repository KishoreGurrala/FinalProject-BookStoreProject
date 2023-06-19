package com.cart.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cart.entity.CartItems;

public interface CartItemsRepository extends JpaRepository<CartItems,Integer>{
	
    @Query(value = "SELECT * FROM CART_ITEMS WHERE   BOOK_ID = :bookId AND EMAIL=:email", nativeQuery = true)
    Optional<CartItems> getByProperty(int bookId,String email);

}
