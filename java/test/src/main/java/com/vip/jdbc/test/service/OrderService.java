package com.vip.jdbc.test.service;

import java.util.List;

import com.vip.jdbc.test.entity.Order;

public interface OrderService {
	void insert(Order order);

	void nestedInsert(Order order);

	void requiresNewInsert(Order order);

	void deleteById(Long id);

	void update(Order order);

	Order selectById(Long id);

	List<Order> selectByIds(List<Long> ids);

	List<Order> selectBy(Order order);

	Order selectByConstant();
}
