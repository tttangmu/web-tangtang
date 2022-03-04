package org.wdl.hotelSysTest.sys.dao;

import java.sql.Connection;

import org.wdl.hotelTest.bean.OrderDetail;

public interface OrderDetailDao {

	OrderDetail findById(int id);

	void update(OrderDetail orderDetail, Connection connection);

	void addFoods(String[] arrfoodIds, Integer orderId);


}
