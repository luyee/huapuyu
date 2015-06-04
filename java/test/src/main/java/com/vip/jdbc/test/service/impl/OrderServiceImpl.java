package com.vip.jdbc.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vip.jdbc.test.entity.Order;
import com.vip.jdbc.test.repository.OrderMapper;
import com.vip.jdbc.test.service.OrderService;

@Transactional(rollbackFor = Throwable.class)
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Resource(name = "orderMapper")
	private OrderMapper orderMapper;

	
	@Transactional
	public void insert(Order order) {
		orderMapper.insert(order);
	}

	
	@Transactional(propagation = Propagation.NESTED)
	public void nestedInsert(Order order) {
		Order order1 = new Order();
		order1.setId(order.getId() + 1);
		order1.setName("zhuzhen");
		orderMapper.insert(order1);
		orderMapper.insert(order);
	}

	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void requiresNewInsert(Order order) {
		orderMapper.insert(order);
	}

	
	@Transactional
	public void deleteById(Long id) {
		orderMapper.deleteById(id);
	}

	
	@Transactional(readOnly = true)
	public Order selectById(Long id) {
		return orderMapper.selectById(id);
	}

	
	@Transactional
	public void update(Order order) {
		orderMapper.update(order);
	}

	
	@Transactional(readOnly = true)
	public List<Order> selectByIds(List<Long> ids) {
		return orderMapper.selectByIds(ids);
	}

	
	public List<Order> selectBy(Order order) {
		return orderMapper.selectBy(order);
	}

	
	public Order selectByConstant() {
		return orderMapper.selectByConstant();
	}
}
