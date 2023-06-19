package com.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.order.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, String>{
	 @Query(value = "SELECT * FROM Orders_table WHERE Email = :value", nativeQuery = true)
	 List<Orders> findAllWithCondition(@Param("value") String email);
}
