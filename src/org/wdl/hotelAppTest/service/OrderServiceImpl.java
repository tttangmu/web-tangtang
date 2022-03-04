package org.wdl.hotelAppTest.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.wdl.hotelAppTest.dao.DinnerTableDao;
import org.wdl.hotelAppTest.dao.DinnerTableDaoImpl;
import org.wdl.hotelAppTest.dao.OrderDao;
import org.wdl.hotelAppTest.dao.OrderDaoImpl;
import org.wdl.hotelTest.bean.DinnerTable;
import org.wdl.hotelTest.bean.Order;
import org.wdl.hotelTest.bean.OrderDetail;

public class OrderServiceImpl implements OrderService {

	private OrderDao orderDao = new OrderDaoImpl();
	@Override
	public void order(int dinnerTableId, Map<Integer, Integer> shopCar, String total, Integer userId) {
		Order order = new Order();
		
		//拼装订单编码
		StringBuffer orderCode = new StringBuffer();
		orderCode.append("OP-");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		orderCode.append(dateFormat.format(new Date()));
		orderCode.append(UUID.randomUUID().toString());
		
		order.setOrderCode(orderCode.toString());
		order.setTableId(dinnerTableId);
		order.setTotalPrice(Double.valueOf(total));
		
		orderDao.order(order,shopCar,userId);
	}
	@Override
	public List<Order> findByTableId(int dinnerTableId, Integer userId) {
		//根据餐桌的id查询所有未删除的未付款的订单
		List<Order>  orders = orderDao.findByTableId(dinnerTableId,userId);
		
		//遍历所有的订单，根据订单id查询其对于订单明细
		if(orders != null && orders.size()>0) {
			for (Order order : orders) {
				List<OrderDetail>  details = orderDao.findByOrderId(order.getId());
				if(details != null && details.size()>0) {
					order.setOrderDetails(details);
				}
			}
		}
		
		return orders;
	}
	@Override
	public Order findById(int id) {
		return orderDao.findById(id);
	}
	@Override
	public void pay(Order order) {
		 orderDao.pay(order);
	}
	@Override
	public void deleteOrder(Order order) {
		orderDao.deleteOrder(order);
	}
	@Override
	public List<Order> findAll(Integer useId) {
		//根据餐桌的id查询所有未删除的未付款的订单
		List<Order>  orders = orderDao.findAll(useId);
		
		//遍历所有的订单，根据订单id查询其对于订单明细
		if(orders != null && orders.size()>0) {
			for (Order order : orders) {
				List<OrderDetail>  details = orderDao.findByOrderId(order.getId());
				if(details != null && details.size()>0) {
					order.setOrderDetails(details);
				}
				
				DinnerTableDao dinnerTableDao = new DinnerTableDaoImpl();
				//通过餐桌的id查询餐桌
				DinnerTable dinnerTable = dinnerTableDao.findById(order.getTableId());
				
				order.setDinnerTable(dinnerTable);
			}
		}
		
		return orders;
	}

}
