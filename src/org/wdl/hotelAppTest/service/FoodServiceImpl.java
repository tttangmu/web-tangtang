package org.wdl.hotelAppTest.service;

import java.util.List;

import org.wdl.hotelAppTest.dao.FoodDao;
import org.wdl.hotelAppTest.dao.FoodDaoImpl;
import org.wdl.hotelTest.bean.Food;

public class FoodServiceImpl implements FoodService {

	private FoodDao foodDao = new FoodDaoImpl();
	@Override
	public List<Food> findByFoodTypeId(Integer foodTypeId) {
		return foodDao.findByFoodTypeId(foodTypeId);
	}
	@Override
	public Food findById(Integer foodId) {
		return foodDao.findById(foodId);
	}

}
