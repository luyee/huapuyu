package com.anders.jdbc.test.repository;

import java.util.List;

import com.anders.jdbc.test.entity.Order;

public interface OrderMapper {
	public List<Order> selectByIds(List<Long> ids);

	public List<Order> selectBy(Order order);

	public int deleteById(Long id);

	public int insert(Order order);

	// public int insertA(Order order);

	public Order selectById(Long id);

	public Order selectByConstant();

	public int update(Order order);
}