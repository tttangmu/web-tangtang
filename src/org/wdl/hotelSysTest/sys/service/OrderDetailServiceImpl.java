package org.wdl.hotelSysTest.sys.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.wdl.hotelSysTest.sys.dao.OrderDao;
import org.wdl.hotelSysTest.sys.dao.OrderDaoImpl;
import org.wdl.hotelSysTest.sys.dao.OrderDetailDao;
import org.wdl.hotelSysTest.sys.dao.OrderDetailDaoImpl;
import org.wdl.hotelTest.bean.Order;
import org.wdl.hotelTest.bean.OrderDetail;
import org.wdl.hotelTest.util.ConnectionFactory;

public class OrderDetailServiceImpl implements OrderDetailService {
	OrderDetailDao orderDetailDao = new OrderDetailDaoImpl();
	OrderDao orderDao = new OrderDaoImpl();
	@Override
	public OrderDetail findById(int id) {
		return orderDetailDao.findById(id);
	}
	@Override
	public void update(OrderDetail orderDetail, Integer flag) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			connection.setAutoCommit(false);
			orderDetailDao.update(orderDetail,connection);
			
			Order order= orderDao.findById(orderDetail.getOrderId());
			if(flag == 1) {
				//删除
				order.setTotalPrice(order.getTotalPrice() - orderDetail.getFood().getPrice()*orderDetail.getFood().getDiscount()*orderDetail.getBuyNum());
			}else if(flag == 2) {
				//修改数量
				List<OrderDetail> details = orderDao.findByOrderId(order.getId());
				System.out.println("details:"+details);
				
				Double totalPrice = orderDetail.getFood().getPrice()*orderDetail.getFood().getDiscount()*orderDetail.getBuyNum();
				for (OrderDetail orderDetail2 : details) {
					if((orderDetail2.getId() != orderDetail.getId()) && (orderDetail2.getDisabled() != 1)) {
						totalPrice = totalPrice+orderDetail2.getFood().getPrice()*orderDetail2.getFood().getDiscount()*orderDetail2.getBuyNum();
					}
				}
				
				order.setTotalPrice(totalPrice);
			}
			orderDao.update(order);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, null, null);
	}
		
	}
	@Override
	public void addFoods(String[] arrfoodIds,Integer orderId) {
		orderDetailDao.addFoods(arrfoodIds,orderId);
	}

}
