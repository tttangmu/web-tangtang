package org.wdl.hotelAppTest.dao;

import java.util.List;
import java.util.Map;

import org.wdl.hotelTest.bean.Order;
import org.wdl.hotelTest.bean.OrderDetail;

public interface OrderDao {

	void order(Order order, Map<Integer, Integer> shopCar, Integer userId);

	List<Order> findByTableId(int dinnerTableId, Integer userId);

	//通过订单id查询订单明细
	List<OrderDetail> findByOrderId(Integer orderId);

	Order findById(int id);

	void pay(Order order);

	void deleteOrder(Order order);

	List<Order> findAll(Integer useId);

}
