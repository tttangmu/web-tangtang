package org.wdl.hotelSysTest.sys.service;

import java.util.List;

import org.wdl.hotelTest.bean.Order;


public interface OrderService {

	List<Order> find(Integer userId, String orderCode, String orderDate, String dinnerTableId);

	Order findById(int id);

	void update(Order order);

}
