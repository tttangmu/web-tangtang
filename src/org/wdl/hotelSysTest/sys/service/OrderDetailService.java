package org.wdl.hotelSysTest.sys.service;

import org.wdl.hotelTest.bean.OrderDetail;

public interface OrderDetailService {

	OrderDetail findById(int id);

	void update(OrderDetail orderDetail, Integer flag);

	void addFoods(String[] arrfoodIds, Integer orderId);


}
